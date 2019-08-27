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
import kotlinx.android.synthetic.main.home_fragment.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.home_fragment,container,false)

        v.rv_home.layoutManager = LinearLayoutManager(context)
        v.rv_home.setHasFixedSize(false)

        val adapter = HomeAdapter()

        v.rv_home.adapter = adapter

        val homeViewModel = ViewModelProviders.of(this).get(HomeViewMode :: class.java)
        homeViewModel.posts.observe(this, Observer<List<RealEstatePost>> { posts -> run{
            adapter.submitList(posts)
        }  })

        return v
    }
}