package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost

class BookMarksViewModel(application: Application) : AndroidViewModel(application) {

    val bookmarksRepository = BookmarksRepository(application)
    var posts : LiveData<List<RealEstatePost>>
    var isLoading = bookmarksRepository.isLoading
    var isOnline = bookmarksRepository.isOnline
    init {
        posts = bookmarksRepository.posts
    }


    fun getBookMarkedPosts(){
        bookmarksRepository.getBookmarkedPosts()
    }
}