package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository

class PostDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private var realEstatePostsRepository : RealEstatePostsRepository
    init {
        realEstatePostsRepository = RealEstatePostsRepository(application)
    }

    lateinit var newInstanceCurrentPost: RealEstatePost

    var currentPost : RealEstatePost? = null
        get(){
            var post = realEstatePostsRepository.getPostByLink(newInstanceCurrentPost.link!!)
            if(post == null){
               post = newInstanceCurrentPost
            }
            field = post
            return field
        }
}