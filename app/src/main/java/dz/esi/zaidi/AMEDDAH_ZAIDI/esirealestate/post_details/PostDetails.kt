package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R

class PostDetails : AppCompatActivity() {

    companion object{
        const val PICTURES = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.PICTURES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        val pictures = intent.getStringArrayListExtra(PICTURES)
        val imageSliderFragment = ImageSliderFragment.newInstance(pictures)
        supportFragmentManager.
            beginTransaction().
            replace(R.id.fragment_image_slider,imageSliderFragment).
            commit()
    }
}
