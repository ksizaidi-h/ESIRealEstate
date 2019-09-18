package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.users.User
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePostsRepository

class ActionsViewModel(application: Application) : AndroidViewModel(application){
    private var realEstatePostsRepository : RealEstatePostsRepository = RealEstatePostsRepository(application)
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()


    fun addToFavorite(post : RealEstatePost){
        realEstatePostsRepository.insertRealEstatePost(post)
    }

    fun removeFromFavorite(post : RealEstatePost){
        realEstatePostsRepository.deleteRealEstatePost(post)
    }

    fun bookmarkLink(link : String){
        if (auth.uid == null) {
            Log.w("hello","user no signed in ")
        }
        else {
            db
                .collection("users")
                .document(auth.uid!!)
                .update("bookmarks", FieldValue.arrayUnion(link))
            User.links.add(link)
        }

    }

    fun unBookmarkLink(link : String){
        if (auth.uid == null) {
            Log.w("hello","user no signed in ")
        }
        else {
            db
                .collection("users")
                .document(auth.uid!!)
                .update("bookmarks", FieldValue.arrayRemove(link))
            User.links.remove(link)
        }

    }



}