package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.image_slider.view.*

class ImageSliderFragment : Fragment() {

    companion object {

        private const val PICTURES = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.PICTURES"

        fun newInstance(pictures : ArrayList<String>) : ImageSliderFragment{
            val fragment = ImageSliderFragment()
            val args = Bundle()
            args.putStringArrayList(PICTURES, pictures)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.image_slider,container,false)

        var pictures =  ArrayList<String>()
        if (arguments != null){
            pictures = arguments?.get(PICTURES) as ArrayList<String>
        }
        val imageSliderAdapter = ImageSliderAdapter(context!!, pictures)
        v.view_pager.adapter = imageSliderAdapter
        v.tabDots.setupWithViewPager(v.view_pager,true)
        return v
    }


}