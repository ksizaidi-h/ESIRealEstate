package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.RealEstateDatabase
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.RealEstatePostDAO
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerieAnnonceWebsite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstatePostsConsumer
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstateWebSite
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class RealEstatePostsRepository(application: Application) : RealEstatePostsConsumer{

    private val postsCache : MutableList<RealEstatePost>
    private lateinit var realEstatePostDAO : RealEstatePostDAO

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

    }

    fun fetchPosts(){
        if(posts.value == null){
            posts.value = ArrayList<RealEstatePost>()
            for(site in postsSources){
                site.getRealEstatePosts(this)
            }
        }else{
            posts.value = postsCache
        }
    }


    override fun addPosts(newPosts: List<RealEstatePost>) {
        posts.value = newPosts
        postsCache.addAll(posts.value!!)
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

    fun getPostsByCategory(category : String) : LiveData<List<RealEstatePost>>{
        doAsyncResult {
            val posts = realEstatePostDAO.getPostsByCategory(category)
            uiThread {
                it.posts.value = posts
            }
        }.get()
        return posts
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