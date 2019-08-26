package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.HomeAdapter
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.HomeViewMode
import kotlinx.android.synthetic.main.activity_main.*
import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
//import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var homeViewModel : HomeViewMode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.setHasFixedSize(false)

        val adapter = HomeAdapter()

        rv_home.adapter = adapter

        homeViewModel = ViewModelProviders.of(this).get(HomeViewMode :: class.java)
        homeViewModel.posts.observe(this, Observer<List<RealEstatePost>> {posts -> run{
            adapter.submitList(posts)
            for(post in posts) {
                Log.d("MainActivity",post.link)
            }
        }  })


//        doAsync {
//            val page = Jsoup.connect("https://www.algerimmo.com/achat-vente/achat-vente-maison/vente-villa-r2-boudouaou-2075.htm").get()
//            val elements = page.select(".info > ul li")
//            val info = ArrayList<String>()
//
//            for(element in elements) {
//                val value = element.text()
//                val tokens = value.split(":")
//                info.add(tokens[1].trim())
//            }
//
//            Log.i("Info", info[0])
//            Log.i("Info", info[1])
//            Log.i("Info", info[2].replace("Wilaya de ",""))
//            Log.i("Info", info[3])
//
//        }




//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level =   HttpLoggingInterceptor.Level.BODY
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://www.algerimmo.com/")
//            .addConverterFactory(RssConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//
//        val service = retrofit.create(RssService::class.java)
//        service.getRss("rss/?category=achat-vente-appartement&type=0&location=16")
//            .enqueue(object : Callback<RssFeed> {
//                override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
//                    val items = response.body()?.items
//                    if(items == null){
//                        Toast.makeText(applicationContext,"Couldn't load", Toast.LENGTH_SHORT).show()
//                        Log.e("Error","load failed")
//                        return
//                    }
//
//                    for(item in items){
//                        Log.i("Post/Date", item.publishDate)
//                        Log.i("Post/Link", item.link)
//                    }
//                }
//
//                override fun onFailure(call: Call<RssFeed>, t: Throwable) {
//                    // Show failure message
//                }
//            })
    }
}
