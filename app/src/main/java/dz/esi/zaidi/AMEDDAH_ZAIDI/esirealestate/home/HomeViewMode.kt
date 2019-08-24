package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository

class HomeViewMode : ViewModel() {
    private var realEstatePostsRepository = RealEstatePostsRepository()
    var posts : LiveData<List<RealEstatePost>>

    init {
        posts = realEstatePostsRepository.posts
    }
}