package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R

class DescriptionFragment : Fragment() {

    companion object {
        private const val WILAYA = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Wilaya"
        private const val COMMUNE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Commune"
        private const val ADDRESS = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Address"
        private const val CATEGORY = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Category"
        private const val TYPE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Type"
        private const val PRICE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Price"
        private const val SURFACE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Surface"
        private const val DESCRIPTION = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.DescriptionFragment.Description"

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

        if (arguments != null){
            val wilaya = arguments?.getString(WILAYA)
            val commune = arguments?.getString(COMMUNE)
            val address = arguments?.getString(ADDRESS)
            val price = arguments?.getString(PRICE)
            val surface = arguments?.getString(SURFACE)
            val category = arguments?.getString(CATEGORY)
            val type = arguments?.getString(TYPE)
            val description = arguments?.getString(DESCRIPTION)
        }

        return v
    }
}