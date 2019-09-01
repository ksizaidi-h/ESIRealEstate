package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.PostsAdapter
import kotlinx.android.synthetic.main.wilaya_subscription_fragment.view.*

class WilayaSubscriptionFragment : Fragment(){
    private lateinit var wilayaSubscriptionViewModel: WilayaSubscriptionViewModel
    private lateinit var adapter : WilayaSubscriptionAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.wilaya_subscription_fragment,container,false)

        v.rv_wilayas.layoutManager = LinearLayoutManager(context)
        v.rv_wilayas.setHasFixedSize(true)

        wilayaSubscriptionViewModel = ViewModelProviders.of(this).get(WilayaSubscriptionViewModel::class.java)
        adapter = WilayaSubscriptionAdapter(wilayaSubscriptionViewModel.subscribedWilayas)
        adapter.onSubscriptionListener = onSubscribeListener
        val wilayas = resources.getStringArray(R.array.wilayas_array)
        v.rv_wilayas.adapter = adapter
        adapter.submitList(wilayas.toList())
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private val onSubscribeListener = object : WilayaSubscriptionAdapter.OnSubscriptionListener{
        override fun onChecked(wilaya : String) {
            wilayaSubscriptionViewModel.subscribeToWilaya(wilaya )
            Toast.makeText(context, context?.getString(R.string.subscribe_toast,wilaya), Toast.LENGTH_SHORT).show()
        }

        override fun onUnchecked(wilaya : String) {
            wilayaSubscriptionViewModel.unSubScribeFromWilaya(wilaya)
            Toast.makeText(context, context?.getString(R.string.unsubscribe_toast,wilaya), Toast.LENGTH_SHORT).show()
        }

    }
}