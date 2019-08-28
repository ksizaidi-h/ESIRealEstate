package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.actions_fragment.view.*

class ActionsFragment : Fragment() {

    companion object{

        const val LINK = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.ActionsFragment.Phone"


        fun newInstance(link : String) : ActionsFragment{
            val fragment = ActionsFragment()
            val args = Bundle()

            args.putString(LINK, link)

            fragment.arguments = args
            return fragment
        }
    }

    private var link = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.actions_fragment, container, false)
        if(arguments != null){
            link = arguments?.getString(LINK)!!
        }

        v.btn_visit_link.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

        return v
    }

}