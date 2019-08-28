package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.post_description_fragment.view.*

class DescriptionFragment : Fragment() {

    companion object {
        const val WILAYA = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Wilaya"
        const val COMMUNE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Commune"
        const val ADDRESS = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Address"
        const val CATEGORY = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Category"
        const val TYPE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Type"
        const val PRICE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Price"
        const val SURFACE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Surface"
        const val DESCRIPTION = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Description"

        fun newInstance(
            wilaya : String,
            commune : String,
            address : String,
            category: String,
            type : String,
            price : String,
            surface : String,
            description : String
        ):DescriptionFragment{
            val fragment = DescriptionFragment()
            val args = Bundle()

            args.putString(WILAYA,wilaya)
            args.putString(COMMUNE,commune)
            args.putString(ADDRESS, address)
            args.putString(CATEGORY,category)
            args.putString(TYPE, type)
            args.putString(PRICE,price)
            args.putString(SURFACE,surface)
            args.putString(DESCRIPTION,description)

            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.post_description_fragment,container,false)

        var wilaya = ""
        var commune = ""
        var address = ""
        var price = ""
        var surface = ""
        var category = ""
        var type = ""
        var description = ""
        if (arguments != null){
            wilaya = arguments?.getString(WILAYA)!!
            commune = arguments?.getString(COMMUNE)!!
            address = arguments?.getString(ADDRESS)!!
            price = arguments?.getString(PRICE)!!
            surface = arguments?.getString(SURFACE)!!
            category = arguments?.getString(CATEGORY)!!
            type = arguments?.getString(TYPE)!!
            description = arguments?.getString(DESCRIPTION)!!
        }

        v.tv_location_post_details.text = context?.getString(R.string.location, wilaya,commune,address)
        v.tv_price_post_details.text = context?.getString(R.string.price,price)
        v.tv_category_post_details.text = context?.getString(R.string.category, category,type)
        v.tv_description_post_details.text = context?.getString(R.string.description, description)
        v.tv_surface_post_details.text = context?.getString(R.string.surface, surface)
        return v
    }

}