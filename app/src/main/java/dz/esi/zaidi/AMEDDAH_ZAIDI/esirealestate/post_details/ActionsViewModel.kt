package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository

class ActionsViewModel(application: Application) : AndroidViewModel(application){
    private var realEstatePostsRepository : RealEstatePostsRepository
    init {
        realEstatePostsRepository = RealEstatePostsRepository(application)
    }
    fun addToFavorite(post : RealEstatePost){
        realEstatePostsRepository.insertRealEstatePost(post)
    }

    fun removeFromFavorite(post : RealEstatePost){
        realEstatePostsRepository.deleteRealEstatePost(post)
    }
}