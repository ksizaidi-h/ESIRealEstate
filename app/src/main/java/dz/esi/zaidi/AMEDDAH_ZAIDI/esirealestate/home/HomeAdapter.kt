package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.ActionsFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.ContactFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.DescriptionFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.ImageSliderFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.PostDetailsActivity
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
                return oldItem.link  == newItem.link
            }

            override fun areContentsTheSame(oldItem: RealEstatePost, newItem: RealEstatePost): Boolean {
                return oldItem == newItem
            }

        }
    }




    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item : RealEstatePost) = with(itemView){
            tv_description_post_item.text = item.description
            tv_post_item_price.text = context.getString(R.string.post_item_price, item.price)
            tv_post_item_wilaya.text = item.wilaya
            if(item.pictures.size > 0) {
                Picasso
                    .get()
                    .load(item.pictures[0])
                    .fit()
                    .into(iv_post_item)
            }else{
                Picasso
                    .get()
                    .load(R.drawable.no_images)
                    .fit()
                    .into(iv_post_item)
            }

            setOnClickListener{
                val intent = Intent(context, PostDetailsActivity::class.java)
                intent.putCharSequenceArrayListExtra(ImageSliderFragment.PICTURES, ArrayList(item.pictures))
                intent.putExtra(DescriptionFragment.DESCRIPTION, item.description)
                intent.putExtra(DescriptionFragment.TYPE, item.type)
                intent.putExtra(DescriptionFragment.CATEGORY, item.category)
                intent.putExtra(DescriptionFragment.SURFACE, item.surface)
                intent.putExtra(DescriptionFragment.PRICE, item.price)
                intent.putExtra(DescriptionFragment.WILAYA, item.wilaya)
                intent.putExtra(DescriptionFragment.COMMUNE, item.commune)
                intent.putExtra(DescriptionFragment.ADDRESS, item.address)
                intent.putExtra(ContactFragment.PHONE,item.phone)
                intent.putExtra(ActionsFragment.LINK, item.link)
                context.startActivity(intent)
            }
        }
    }
}