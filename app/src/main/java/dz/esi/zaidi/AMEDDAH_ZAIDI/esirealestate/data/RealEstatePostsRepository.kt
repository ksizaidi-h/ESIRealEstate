package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerimmoWebSite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstateWebSite

class RealEstatePostsRepository{
    private val postsSources = arrayOf<RealEstateWebSite>(
        AlgerimmoWebSite(this)
    )

    var posts = MutableLiveData<List<RealEstatePost>>()

    init {
        for(site in postsSources){
            site.getRealEstatePosts()
        }
    }
}