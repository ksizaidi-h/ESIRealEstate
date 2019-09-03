package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private var realEstatePostsRepository = RealEstatePostsRepository(application)
    var posts : LiveData<List<RealEstatePost>>


    init {
        posts = realEstatePostsRepository.favoritePosts
    }

    fun getFavoritePosts(category : String) : Boolean{
        return !realEstatePostsRepository.getPostsByCategory(category).value.isNullOrEmpty()
    }

}