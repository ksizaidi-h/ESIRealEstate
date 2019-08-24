package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.RssService
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.Localisation
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePoster
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository
import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class AlgerimmoWebSite(val realEstatePostsRepository: RealEstatePostsRepository) : RealEstateWebSite {

    companion object{
        private const val URL = "https://www.algerimmo.com/"
    }

    override fun getRealEstatePosts(): List<RealEstatePost> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.algerimmo.com/")
            .addConverterFactory(RssConverterFactory.create())
            .build()

        val posts = ArrayList<RealEstatePost>()

        Log.d("Algerimmo","In getRealEstatePosts")

        val service = retrofit.create(RssService::class.java)
        service.getRss("rss/")
            .enqueue(object : Callback<RssFeed> {
                override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                    val items = response.body()?.items
                    if(items == null){
                        Log.e("Error","load failed")
                        return
                    }
                    doAsync {
                        for(item in items){
                            val description = item.description
                            val page = Jsoup.connect(item.link).get()
                            Log.d("Algerimmo",item.link)
                            val images = page.select(".picture a")
                            val pictures = ArrayList<String>()
                            for (image in images){
                               pictures.add(image.absUrl("href"))
                            }

                            val elements = page.select(".info > ul li")
                            val info = ArrayList<String>()

                            for(element in elements) {
                                val value = element.text()
                                val tokens = value.split(":")
                                info.add(tokens[1].trim())
                            }
                            val phone = info[0]
                            val fullName = info[1]
                            val town = info[2].replace("Wilaya de ","")
                            val city = info[3]

                            val localisation = Localisation(town, city)
                            val poster = RealEstatePoster(fullName, phone)

                            val post = RealEstatePost(
                                description,
                                localisation,
                                poster,
                                pictures,
                                item.link
                            )
                            posts.add(post)
                        }
                        realEstatePostsRepository.posts.postValue(posts)
                    }

                }

                override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                    Log.d("Algerimmo", "Fail to get RsS feed")
                    throw Exception(t.message)
                }
            })
        Log.d("Algerimmo/Return",posts.size.toString())
        return posts
    }
}