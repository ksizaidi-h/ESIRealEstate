package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.MainActivity
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks.LoginActivity
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.nav_header.view.iv_user_photo
import kotlinx.android.synthetic.main.nav_header.view.tv_user_email
import kotlinx.android.synthetic.main.nav_header.view.tv_user_name
import kotlinx.android.synthetic.main.user_fargment.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.user_fargment, container, false)
        val auth = FirebaseAuth.getInstance()
        if (User.isLoggedIn) {


            v.tv_user_name_profile.text = User.name
            v.tv_user_email_profile.text = User.email
            Picasso.get().load(User.urlPhoto) to v.iv_user_photo_profile

            v.btn_logout.setOnClickListener {
                auth.signOut()
                User.isLoggedIn = false
                User.email = ""
                User.links = ArrayList()
                User.name = ""
                User.urlPhoto = null

                val intent = Intent(activity!!, MainActivity::class.java)
                startActivity(intent)

            }
        }

        return v
    }
}