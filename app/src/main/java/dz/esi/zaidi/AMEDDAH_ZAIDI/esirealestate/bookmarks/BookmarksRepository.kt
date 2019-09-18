package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks

import android.app.Application
import androidx.lifecycle.MutableLiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.Utils
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.AlgerieAnnonceWebsite
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.BookmarkedRealEstatePostsProvider
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources.RealEstatePostsConsumer

class BookmarksRepository(val application: Application) : RealEstatePostsConsumer{

    val context = application.applicationContext
    var isLoading = MutableLiveData<Boolean>()
    var isOnline = MutableLiveData<Boolean>()
    private  var postsCache : MutableList<RealEstatePost>
    val posts = MutableLiveData<List<RealEstatePost>>()

    override fun addPost(newPost: RealEstatePost) {
        postsCache.add(newPost)
        posts.postValue(postsCache)
        isLoading.postValue(false)
    }

    var bookmarkedPosts : MutableLiveData<List<RealEstatePost>>

    private val bookmarkSources = arrayOf<BookmarkedRealEstatePostsProvider>(
        /*AlgerimmoWebSite(),*/ AlgerieAnnonceWebsite()
    )

    init {
        bookmarkedPosts = MutableLiveData()
        postsCache = ArrayList()
    }


    fun getBookmarkedPosts(){
        isOnline.value = Utils.isInternetAvailable(context)
        if (isOnline.value == true){
            isLoading.value = true
            if(posts.value == null){
                posts.value = ArrayList<RealEstatePost>()
                for(site in bookmarkSources){
                    site.getBookmarkedPosts(this)
                }
            }else{
                posts.value = postsCache
                if (!postsCache.isEmpty()){
                    isLoading.value = false
                }

            }
        }
    }

    fun refreshBookmarks(){
        isOnline.value = Utils.isInternetAvailable(context)
        if (isOnline.value == true){
            isLoading.value = true
                posts.value = ArrayList<RealEstatePost>()
                for(site in bookmarkSources){
                    site.getBookmarkedPosts(this)
                }
            isLoading.value = false
        }
    }
}