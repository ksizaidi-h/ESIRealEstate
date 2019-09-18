package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.accounts.Account
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

object User {
    lateinit var email : String
    var links = ArrayList<String>()
    var isLoggedIn = false
    lateinit var name : String
    var urlPhoto : Uri? = null
}