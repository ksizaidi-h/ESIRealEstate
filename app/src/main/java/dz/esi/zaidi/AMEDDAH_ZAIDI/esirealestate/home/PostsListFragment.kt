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

open class PostsListFragment : Fragment() {

    private lateinit var adapter: PostsAdapter
    private lateinit var v: View
    private lateinit var viewModel: PostsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.posts_list_fragment, container, false)

        v.rv_home.layoutManager = LinearLayoutManager(context)
        v.rv_home.setHasFixedSize(false)

        adapter = PostsAdapter()

        v.rv_home.adapter = adapter



        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(PostsListViewModel::class.java)
        viewModel.isLoading.observe(this, Observer {
            if (it == true) {
                pb_waiting.visibility = View.VISIBLE
                rv_home.visibility = View.GONE
            } else {
                pb_waiting.visibility = View.GONE
                rv_home.visibility = View.VISIBLE
            }
        })
        viewModel.posts.observe(this, Observer<List<RealEstatePost>> { posts ->
            run {
                adapter.submitList(posts)
            }
        })


        super.onActivityCreated(savedInstanceState)
    }
}