package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.wilayas_subscription_item.view.*

class WilayaSubscriptionAdapter(val subscribedWilayas: List<String>) : ListAdapter<String, WilayaSubscriptionAdapter.WilayasViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WilayasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wilayas_subscription_item, parent,false)
        return WilayasViewHolder(view,subscribedWilayas,onSubscriptionListener)
    }

    override fun onBindViewHolder(holder: WilayasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object{
        private val DIFF_CALLBACK  = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem  == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    class WilayasViewHolder(itemView: View,val subscribedWilayas : List<String>, val onSubscriptionListener: OnSubscriptionListener) : RecyclerView.ViewHolder(itemView){
        fun bind(item : String) = with(itemView){
            itemView.tv_wilaya_subscription_item.text = item
            itemView.btn_subscribe.isChecked = item in subscribedWilayas
            (itemView.btn_subscribe).setOnClickListener {
                if(itemView.btn_subscribe.isChecked){
                    onSubscriptionListener.onChecked(item)
                }else{
                    onSubscriptionListener.onUnchecked(item)
                }
            }
        }
    }

    interface OnSubscriptionListener{
        fun onChecked(wilaya : String)
        fun onUnchecked(wilaya : String)
    }
    lateinit var onSubscriptionListener: OnSubscriptionListener
}