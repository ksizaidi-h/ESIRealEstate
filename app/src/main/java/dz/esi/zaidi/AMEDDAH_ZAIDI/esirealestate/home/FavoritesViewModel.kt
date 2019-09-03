package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private var realEstatePostsRepository = RealEstatePostsRepository(application)
    var currentCategory  = MutableLiveData<String>()
    var posts : LiveData<List<RealEstatePost>>


    init {
        posts = realEstatePostsRepository.favoritePosts
    }

    fun getFavoritePosts(category : String) : Boolean{
        currentCategory.value = category
        return !realEstatePostsRepository.getPostsByCategory(category).value.isNullOrEmpty()
    }

}