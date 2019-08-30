package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources


import android.util.Log
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.Localisation
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePoster
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import java.net.URL

class AlgerieAnnonceWebsite : RealEstateWebSite {

    companion object{
        private const val baseUrl = "http://www.annonce-algerie.com/"
        private const val TAG = "AAW"
    }

    override fun getRealEstatePosts(consumer: RealEstatePostsConsumer){

        val categoryRange = 1
        val typeRage = 2
        val linkRange = 3
        val priceRage = 4
        doAsync{
            val posts = ArrayList<RealEstatePost>()
            val connection = Jsoup
                .connect(baseUrl + "/AnnoncesImmobilier.asp?rech_page_num=1&rech_cod_cat=1&rech_cod_rub=&rech_cod_typ=&rech_cod_sou_typ=&rech_cod_pay=DZ&rech_cod_reg=&rech_cod_vil=&rech_cod_loc=&rech_prix_min=&rech_prix_max=&rech_surf_min=&rech_surf_max=&rech_age=&rech_photo=&rech_typ_cli=&rech_order_by=11")
                .execute()
                .charset("ISO-8859-15")

            val page = Jsoup.parse(connection.body())

            val infos = page.select(".Tableau1")

            for (info in infos){
                val values = info.select("td:nth-child(2n)")
                val category = values[categoryRange].text()
                val type = values[typeRage].text()
                val link = baseUrl + values[linkRange].select("a").attr("href")
                val price = values[priceRage].text()

                // Inside post page
                val insidePostExecution = Jsoup
                    .connect(link)
                    .execute()
                    .charset("ISO-8859-15")
                val insidePostPage = Jsoup.parse(insidePostExecution.body())

                // Retrieve poster

                var phone = insidePostPage.select("li.cellphone span.da_contact_value").text()
                if(phone == ""){
                    phone = insidePostPage.select("li.phone span.da_contact_value").text()
                }
                Log.d(TAG + "/phone",phone)

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
                    picturesURLs.add(baseUrl + picture.attr("src"))
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

}