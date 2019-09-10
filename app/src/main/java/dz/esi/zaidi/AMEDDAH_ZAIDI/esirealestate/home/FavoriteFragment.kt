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
import kotlinx.android.synthetic.main.favorite_list_fragment.*
import kotlinx.android.synthetic.main.favorite_list_fragment.view.*
import kotlinx.android.synthetic.main.posts_list_fragment.view.*

class FavoriteFragment : Fragment() {

    lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.favorite_list_fragment, container, false)

        v.rv_favorite.layoutManager = LinearLayoutManager(context)
        v.rv_favorite.setHasFixedSize(false)

        adapter = PostsAdapter()

        v.rv_favorite.adapter = adapter
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(FavoritesViewModel::class.java)
        viewModel.currentCategory.observe(activity!!, Observer {
            view?.empty_view?.text = getString(R.string.no_posts, it)
        })
        viewModel.posts.observe(activity!!, Observer { posts ->
            run {
                Log.d("Posts : ${viewModel.currentCategory.value}", "${posts}")
                adapter.submitList(listOf(*posts.toTypedArray()))
                if (posts.isNullOrEmpty()) {
                    Log.d("FavoriteFragment", "empty")
                    view?.rv_favorite?.visibility = View.GONE
                    view?.empty_view?.visibility = View.VISIBLE
                } else {
                    Log.d("FavoriteFragment", "not empty")
                    view?.rv_favorite?.visibility = View.VISIBLE
                    view?.empty_view?.visibility = View.GONE
                }
            }
        }
        )
        super.onActivityCreated(savedInstanceState)
    }


}