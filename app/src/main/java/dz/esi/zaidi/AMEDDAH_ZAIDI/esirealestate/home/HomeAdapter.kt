package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import kotlinx.android.synthetic.main.real_estate_post_item.view.*

class HomeAdapter : ListAdapter<RealEstatePost, HomeAdapter.PostsViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.real_estate_post_item,parent,false)
            return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        private val DIFF_CALLBACK  = object : DiffUtil.ItemCallback<RealEstatePost>(){
            override fun areItemsTheSame(oldItem: RealEstatePost, newItem: RealEstatePost): Boolean {
                return oldItem.postId  == newItem.postId
            }

            override fun areContentsTheSame(oldItem: RealEstatePost, newItem: RealEstatePost): Boolean {
                return oldItem == newItem
            }

        }
    }


    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item : RealEstatePost) = with(itemView){
            tv_description_post_item.text = item.description
            if (item.pictures.size > 0) {
                Picasso.get().load(item.pictures[0]).into(iv_post_item)
            }else{
                //TODO
            }
        }
    }
}