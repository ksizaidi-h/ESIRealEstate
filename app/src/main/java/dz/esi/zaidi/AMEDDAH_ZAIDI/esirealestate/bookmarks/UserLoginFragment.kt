package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R

class UserLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.user,container,false)
        // TODO("Handle buttons clicks")
        return v
    }
}