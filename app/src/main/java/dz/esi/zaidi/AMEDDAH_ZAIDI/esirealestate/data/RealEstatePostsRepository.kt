package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.Utils
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.RealEstateDatabase
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.RealEstatePostDAO
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerieAnnonceWebsite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.BookmarkedRealEstatePostsProvider
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstatePostsConsumer
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstateWebSite
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class RealEstatePostsRepository(application: Application) : RealEstatePostsConsumer{

    private  var postsCache : MutableList<RealEstatePost>
    private lateinit var realEstatePostDAO : RealEstatePostDAO
    var isLoading = MutableLiveData<Boolean>()
    var isOnline = MutableLiveData<Boolean>()
    var favoritePosts : MutableLiveData<List<RealEstatePost>>
    val context: Context = application.applicationContext


    private val postsSources = arrayOf<RealEstateWebSite>(
        /*AlgerimmoWebSite(),*/ AlgerieAnnonceWebsite()
    )

    var posts = MutableLiveData<List<RealEstatePost>>()

    init {
        val database = RealEstateDatabase.getInstance(application)
        if(database != null){
            realEstatePostDAO = database.realEstatePostDao()
        }
        postsCache = ArrayList<RealEstatePost>()
        favoritePosts = MutableLiveData<List<RealEstatePost>>()
        isOnline.value = false

    }

    fun fetchPosts(){
        isOnline.value = Utils.isInternetAvailable(context)
        if (isOnline.value == true){
            isLoading.value = true
            if(posts.value == null){
                posts.value = ArrayList<RealEstatePost>()
                for(site in postsSources){
                    site.getRealEstatePosts(this)
                }
            }else{
                posts.value = postsCache
                if (!postsCache.isEmpty()){
                    isLoading.value = false
                }

            }
        }

    }


    override fun addPost(newPost: RealEstatePost) {
        postsCache.add(newPost)
        posts.postValue(postsCache)
        isLoading.postValue(false)
    }

    fun insertRealEstatePost(post : RealEstatePost){
        doAsync {
            realEstatePostDAO.insertRealEstatePost(post)
        }
    }

    fun deleteRealEstatePost(post: RealEstatePost){
        doAsync {
            realEstatePostDAO.deleteRealEstatePost(post)
        }
    }

    fun getPostsByCategory(category : String) {
        doAsync {
            favoritePosts.postValue(realEstatePostDAO.getPostsByCategory(category))
        }
    }

    fun getPostByLink(link : String) : RealEstatePost?{
        var post : RealEstatePost? = null
        doAsyncResult {

                post = realEstatePostDAO.getPostByLink(link)
            uiThread {
            }
        }.get()
        Log.d("getPostByLink", post.toString())
        return post
    }

}