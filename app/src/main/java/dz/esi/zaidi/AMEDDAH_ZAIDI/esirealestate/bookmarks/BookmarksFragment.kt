package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R

class BookmarksFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.bookmarks_fragment,container,false)
        /*TODO("SHOW THE BOOKMARKED LINKS" +
                "AND OPEN THEM")*/
        return v

    }
}