package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.no_posts_fagment.view.*

class NoPostsFragment : Fragment() {
    companion object{
        const val CATEGORY = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.NoPostsFragment.Categorty"

        fun newInstance(category : String) : NoPostsFragment{
            val fragment = NoPostsFragment()
            val args = Bundle()

            args.putString(CATEGORY, category)

            fragment.arguments = args
            return fragment
        }
    }

    private var category = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.no_posts_fagment,container,false)

        if(arguments != null){
            category = arguments?.getString(CATEGORY)!!
        }
        v.tv_no_posts.text = context?.getString(R.string.no_posts,
            category
        )


        return v
    }

}