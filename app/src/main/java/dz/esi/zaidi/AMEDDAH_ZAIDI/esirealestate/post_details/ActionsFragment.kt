package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.users.User
import kotlinx.android.synthetic.main.actions_fragment.view.*

class ActionsFragment : Fragment() {

    private lateinit var postsDetailsViewModel: PostDetailsViewModel
    private lateinit var actionsViewModel: ActionsViewModel

    companion object{

        const val LINK = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.ActionsFragment.Phone"
        private const val TAG = "SharingBottomSheet"


        fun newInstance(link : String) : ActionsFragment {
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

        postsDetailsViewModel = ViewModelProviders.of(activity!!).get(PostDetailsViewModel::class.java)
        actionsViewModel = ViewModelProviders.of(this).get(ActionsViewModel::class.java)

        if(arguments != null){
            link = arguments?.getString(LINK)!!
        }

        v.btn_visit_link.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

        if (postsDetailsViewModel.currentPost?.postId != 0){
            v.btn_favorites.isChecked = true
        }
        v.btn_favorites.setOnClickListener {
            if (it.btn_favorites.isChecked){
                actionsViewModel.addToFavorite(postsDetailsViewModel.currentPost!!)
                Toast.makeText(context,"Post added to favorite",Toast.LENGTH_SHORT).show()
            }else{
                actionsViewModel.removeFromFavorite(postsDetailsViewModel.currentPost!!)
                Toast.makeText(context,"Post removed from favorite",Toast.LENGTH_SHORT).show()
            }
        }
        if(!User.isLoggedIn){
            v.btn_bookmark.isEnabled = false
        }else{
            v.btn_bookmark.isChecked = link in User.links
            v.btn_bookmark.setOnClickListener {
                if (it.btn_bookmark.isChecked){
                    actionsViewModel.bookmarkLink(link)
                    Toast.makeText(context,"Post added to favorite",Toast.LENGTH_SHORT).show()
                }else{
                    actionsViewModel.unBookmarkLink(link)
                    Toast.makeText(context,"Post removed from favorite",Toast.LENGTH_SHORT).show()
                }
            }
        }

        v.btn_share_post.setOnClickListener {
            val sharingBottomSheet = SharingBottomSheet(link)
            sharingBottomSheet.show(activity!!.supportFragmentManager, TAG)
        }



        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}