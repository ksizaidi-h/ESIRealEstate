package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources


import android.util.Log
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.Localisation
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePoster
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class AlgerieAnnonceWebsite : RealEstateWebSite {

    companion object{
        private const val URL = "http://www.annonce-algerie.com/"
        private const val TAG = "AAW"
    }

    override fun getRealEstatePosts(consumer: RealEstatePostsConsumer){

        val categoryRange = 1
        val typeRage = 2
        val linkRange = 3
        val priceRage = 4
        doAsyncResult{
            val posts = ArrayList<RealEstatePost>()
            val connection = Jsoup
                .connect(URL + "AnnoncesImmobilier.asp")
                .execute()

            val page = Jsoup.parse(connection.body())

            val infos = page.select(".Tableau1")

            for (info in infos){
                val values = info.select("td:nth-child(2n)")
                val category = values[categoryRange].text()
                val type = values[typeRage].text()
                val link = URL + values[linkRange].select("a").attr("href")
                val price = values[priceRage].text()

                // Inside post page

                val insidePostExecution = Jsoup
                    .connect(link)
                    .execute()
                val insidePostPage = Jsoup.parse(insidePostExecution.body())

                // Retrieve poster

                var phone = insidePostPage.select("td.cellphone").text()
                if(phone == ""){
                    phone = insidePostPage.select("td.phone").text()
                }

                //Retrieve localisation

                val labels = insidePostPage.select("td.da_label_field")
                val labelValues = insidePostPage.select("td.da_field_text")
                var i = 0
                var town = ""
                var city = ""
                var address = ""
                var surface = ""
                var text = ""
                for (label in labels){

                    if (label.text() == "Localisation"){
                        val loc = labelValues[i].select("a")
                        town = loc[2].text()
                        city = loc[3].text()
                        Log.d(TAG + "/loc", "$town / $city")
                    }

                    if (label.text() == "Adresse"){
                        address = labelValues[i].text()
                        Log.d(TAG + "/addr", address)
                    }

                    if(label.text() == "Surface"){
                        surface = labelValues[i].text()
                        Log.d(TAG + "/surf", surface)
                    }

                    if(label.text() == "Texte"){
                        text = labelValues[i].text()
                        Log.d(TAG + "/text", text)
                    }

                    i++
                }

                //Retrieve pictures
                val pictures = insidePostPage.select("img.PhotoMin1")
                val picturesURLs = ArrayList<String>()
                for(picture in pictures){
                    picturesURLs.add(URL + picture.attr("src"))
                }

                val localisation = Localisation(town, city)
                localisation.adresse = address

                val poster = RealEstatePoster(null, phone)

                val post = RealEstatePost(text,localisation,poster,picturesURLs,link)
                post.category = category
                post.type = type
                post.price = price
                post.surface = surface

                posts.add(post)
            }
           uiThread {
               consumer.addPosts(posts)
           }
        }

    }

    private fun getLinks() : List<String>{

        val linksList = ArrayList<String>()
        val connection = Jsoup
            .connect(URL + "AnnoncesImmobilier.asp")
            .execute()

        Log.i(TAG,"connection sucessful")

        val page = Jsoup.parse(connection.body())

        val links = page.select(".Tableau1 td:nth-child(8) a")
        for (link in links){
            linksList.add(URL + link.attr("href"))
        }
        return linksList
    }
}