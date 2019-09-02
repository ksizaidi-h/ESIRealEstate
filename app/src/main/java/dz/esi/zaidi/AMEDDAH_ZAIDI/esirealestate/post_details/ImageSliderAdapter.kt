package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R

class ImageSliderAdapter(val context : Context, val pictures : ArrayList<String>) : PagerAdapter(){


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return pictures.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        if (pictures.size > 0){
            Picasso
                .get()
                .load(pictures[position])
                .fit()
                .into(imageView)
            container.addView(imageView)
        }else{
            Picasso
                .get()
                .load(R.drawable.no_images)
                .fit()
                .into(imageView)
            container.addView(imageView)
        }


        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}