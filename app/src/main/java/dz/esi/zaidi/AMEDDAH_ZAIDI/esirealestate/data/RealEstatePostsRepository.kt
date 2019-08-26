package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerieAnnonceWebsite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerimmoWebSite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstatePostsConsumer
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstateWebSite
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class RealEstatePostsRepository : RealEstatePostsConsumer{

    private val postsData : MutableList<RealEstatePost>
    init {
        postsData = ArrayList<RealEstatePost>()
    }


    override fun addPosts(newPosts: List<RealEstatePost>) {
        postsData.addAll(newPosts)
        posts.postValue(postsData)
    }

    private val postsSources = arrayOf<RealEstateWebSite>(
        AlgerimmoWebSite(), AlgerieAnnonceWebsite()
    )

    var posts = MutableLiveData<List<RealEstatePost>>()

    init {
        for(site in postsSources){
            site.getRealEstatePosts(this)
        }
    }
}