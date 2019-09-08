package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources


import android.content.Context
import android.util.Log
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.Localisation
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePoster
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service.WilayaSubscriber
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class AlgerieAnnonceWebsite : RealEstateWebSite, NewPostsNotificationProvider {

    companion object {
        private const val baseUrl = "http://www.annonce-algerie.com/"
        private const val TAG = "AAW"

    }

    private fun getDocument(url: String): Document {
        val connection = Jsoup
            .connect(url)
            .execute()
            .charset("ISO-8859-15")

        val page = Jsoup.parse(connection.body())

        return page
    }

    private fun getPostFromPge(link : String): RealEstatePost {


        val insidePostPage = getDocument(link)

        // Retrieve poster

        var phone = insidePostPage.select("li.cellphone span.da_contact_value").text()
        if (phone == "") {
            phone = insidePostPage.select("li.phone span.da_contact_value").text()
        }
        Log.d(TAG + "/phone", phone)

        //Retrieve localisation

        val labels = insidePostPage.select("td.da_label_field")
        val labelValues = insidePostPage.select("td.da_field_text")
        var i = 0

        val loc = insidePostPage.select(".da_field_text a[href*=cod_vil]")
        val town = loc[0].text()
        val city = loc[1].text()
        Log.d(TAG + "/loc", "$town / $city")

        val catType = insidePostPage.select(".da_field_text a[href*=cod_typ]")
        val category = catType[0].text()
        val type = catType[1].text()

        var address = ""
        val labelText = labels.map { it.text() }

        if ("Adresse" in labelText){
            address = labelValues[labelText.indexOf("Adresse")].text()
            Log.d(TAG + "/addr", address)
        }

        var surface = ""
        if ("Surface" in labelText){
            surface = labelValues[labelText.indexOf("Surface")].text()
            Log.d(TAG + "/surf", surface)
        }


        var text = ""
        text = labelValues[labelText.indexOf("Texte")].text()
        Log.d(TAG + "/text", text)

        val price = labelValues[labelText.indexOf("Prix")].text().replace("Dinar Algèrien (DA)","")

        //Retrieve pictures
        val pictures = insidePostPage.select("img.PhotoMin1")
        val picturesURLs = ArrayList<String>()
        for (picture in pictures) {
            picturesURLs.add(baseUrl + picture.attr("src"))
        }

        val localisation = Localisation(town, city)
        localisation.adresse = address

        val poster = RealEstatePoster(null, phone)

        val post = RealEstatePost(text, localisation, poster, picturesURLs, link)
        post.category = category
        post.type = type
        post.price = price
        post.surface = surface

        return post

    }

    override fun getRealEstatePosts(consumer: RealEstatePostsConsumer) {
        doAsync {
            val page = getDocument(baseUrl + "/AnnoncesImmobilier.asp?rech_order_by=11")

            val links = page.select("a[href*=cod_ann]").map { baseUrl + it.attr("href") }
            for (link in links){
             val post = getPostFromPge(link)
                consumer.addPost(post)
             Log.d("$TAG / Price ","${post.price}")
            }

        }

    }

    override fun getNewPosts(consumer: NotificationConsumer, context: Context) {
        val wilayaSubscriber = WilayaSubscriber(context)
        doAsync{
            val page = getDocument(baseUrl)
            val wilayaLinks = page.select("a[href*=cod_reg]")


            val filteredLinks = wilayaLinks.filter {
                it.text().replace("\\([0-9]+\\)".toRegex(),"").trim() in wilayaSubscriber.subscribedWilayas.map { it.wilayaName }
            }

            for (link in filteredLinks) {
                val wilayaName = link.text().replace("\\([0-9]+\\)".toRegex(),"").trim()
                Log.d(TAG, "getNewPosts : you are subscribed to $wilayaName")

                val value = baseUrl + link.attr("href") + "&rech_order_by=11"
                Log.d(TAG ,"getNewPosts : wilayaLink : $value")
                val wilayaPage = getDocument(value)
                val lastPosted = baseUrl + wilayaPage.select("a[href*=cod_ann]").first().attr("href")
                Log.d(TAG,"getNewPosts : lastPosted $wilayaName : $lastPosted")
                val indexOfWilaya =
                    wilayaSubscriber.subscribedWilayas.indexOfFirst { it.wilayaName == wilayaName }
                Log.d(TAG , "getNewPosts : indexOfWilaya $wilayaName : $indexOfWilaya")
                if (wilayaSubscriber.subscribedWilayas[indexOfWilaya].lastLink == null) {
                    wilayaSubscriber.subscribedWilayas[indexOfWilaya].lastLink = lastPosted
                    Log.d(TAG, "$lastPosted saved to $wilayaName")
                    wilayaSubscriber.saveChanges()
                } else if (wilayaSubscriber.subscribedWilayas[indexOfWilaya].lastLink != lastPosted) {
                    wilayaSubscriber.subscribedWilayas[indexOfWilaya].lastLink = lastPosted
                    wilayaSubscriber.saveChanges()
                    val post = getPostFromPge(lastPosted)
                    Log.d(TAG,"getNewPosts : should show a notification for  $wilayaName ${post}")
                    uiThread {
                        consumer.makeNotification(post)
                    }

                }else{
                    Log.d(TAG, "getNewPosts : No new posts available for $wilayaName")
                }
            }
        }
    }
}