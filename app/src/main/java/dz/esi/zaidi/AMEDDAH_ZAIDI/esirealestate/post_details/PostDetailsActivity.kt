package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.Localisation
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePoster

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var postsDetailsViewModel: PostDetailsViewModel

    companion object{
        var SHOW_POST_DETAILS_CODE = 1
        fun createIntent(context:Context,
                         post : RealEstatePost) : Intent{
            val intent = Intent(context, PostDetailsActivity::class.java)
            intent.putCharSequenceArrayListExtra(ImageSliderFragment.PICTURES, ArrayList(post.pictures))
            intent.putExtra(DescriptionFragment.DESCRIPTION, post.description)
            intent.putExtra(DescriptionFragment.TYPE, post.type)
            intent.putExtra(DescriptionFragment.CATEGORY, post.category)
            intent.putExtra(DescriptionFragment.SURFACE, post.surface)
            intent.putExtra(DescriptionFragment.PRICE, post.price)
            intent.putExtra(DescriptionFragment.WILAYA, post.wilaya)
            intent.putExtra(DescriptionFragment.COMMUNE, post.commune)
            intent.putExtra(DescriptionFragment.ADDRESS, post.address)
            intent.putExtra(ContactFragment.PHONE,post.phone)
            intent.putExtra(ActionsFragment.LINK, post.link)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        val pictures = intent.getStringArrayListExtra(ImageSliderFragment.PICTURES)
        val imageSliderFragment = ImageSliderFragment.newInstance(pictures)

        postsDetailsViewModel = ViewModelProviders.of(this).get(PostDetailsViewModel::class.java)

        supportFragmentManager.
            beginTransaction().
            replace(R.id.fragment_image_slider,imageSliderFragment).
            commit()

        val wilaya = intent.getStringExtra(DescriptionFragment.WILAYA)!!
        val commune = intent.getStringExtra(DescriptionFragment.COMMUNE)!!
        val address = intent.getStringExtra(DescriptionFragment.ADDRESS)!!
        val category = intent.getStringExtra(DescriptionFragment.CATEGORY)!!
        val type = intent.getStringExtra(DescriptionFragment.TYPE)!!
        val price = intent.getStringExtra(DescriptionFragment.PRICE)!!
        val surface = intent.getStringExtra(DescriptionFragment.SURFACE)!!
        val description = intent.getStringExtra(DescriptionFragment.DESCRIPTION)!!



        val descriptionFragment = DescriptionFragment.newInstance(
            wilaya,
            commune,
            address,
            category,
            type,
            price,
            surface,
            description
        )

        supportFragmentManager.
            beginTransaction().
            replace(R.id.fragment_post_description_container,descriptionFragment).
            commit()

        val phone = intent.getStringExtra(ContactFragment.PHONE)!!
        val contactFragment = ContactFragment.newInstance(phone)
        supportFragmentManager.
            beginTransaction().
            replace(R.id.fragment_contact_container,contactFragment).
            commit()

        val link = intent.getStringExtra(ActionsFragment.LINK)!!
        val actionsFragment = ActionsFragment.newInstance(link)
        supportFragmentManager.
            beginTransaction().
            replace(R.id.actions_container,actionsFragment).
            commit()

        val localisation = Localisation(wilaya, commune)
        localisation.adresse = address

        val poster = RealEstatePoster(null, phone)

        val post = RealEstatePost(description,localisation,poster,pictures,link)
        post.category = category
        post.type = type
        post.price = price
        post.surface = surface

        postsDetailsViewModel.newInstanceCurrentPost = post
    }


}
