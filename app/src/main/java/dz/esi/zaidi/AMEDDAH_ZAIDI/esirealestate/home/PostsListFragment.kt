package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import kotlinx.android.synthetic.main.posts_list_fragment.*
import kotlinx.android.synthetic.main.posts_list_fragment.view.*

class PostsListFragment : Fragment() {

    private lateinit var adapter : PostsAdapter
    private lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.posts_list_fragment,container,false)

        v.rv_home.layoutManager = LinearLayoutManager(context)
        v.rv_home.setHasFixedSize(false)

        adapter = PostsAdapter()

        v.rv_home.adapter = adapter



        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val homeViewModel = ViewModelProviders.of(activity!!).get(PostsListViewModel :: class.java)
        homeViewModel.posts.observe(this, Observer<List<RealEstatePost>> { posts -> run{
            adapter.submitList(posts)
            if(posts.isNullOrEmpty()){
                rv_home.visibility = View.GONE
                empty_view.visibility = View.VISIBLE
            }else{
                pb_waiting.visibility = View.GONE
                rv_home.visibility = View.VISIBLE
                empty_view.visibility = View.GONE
            }
        }  })
        empty_view.visibility = View.GONE

        super.onActivityCreated(savedInstanceState)
    }
}