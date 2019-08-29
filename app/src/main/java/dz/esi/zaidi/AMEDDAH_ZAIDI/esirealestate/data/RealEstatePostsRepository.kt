package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.RealEstateDatabase
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.RealEstatePostDAO
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerieAnnonceWebsite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerimmoWebSite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstatePostsConsumer
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstateWebSite
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class RealEstatePostsRepository(application: Application) : RealEstatePostsConsumer{

    private val postsData : MutableList<RealEstatePost>
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
        postsData = ArrayList<RealEstatePost>()

    }

    fun fetchPosts(){
        for(site in postsSources){
            site.getRealEstatePosts(this)
        }
    }


    override fun addPosts(newPosts: List<RealEstatePost>) {
        postsData.addAll(newPosts)
        posts.postValue(postsData)
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