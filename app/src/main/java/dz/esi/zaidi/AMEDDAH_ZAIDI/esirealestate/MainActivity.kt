package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.PostsListFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.PostsListViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service.WilayaSubscriptionFragment

//import java.util.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    private lateinit var postsListViewModel : PostsListViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView : NavigationView
    private lateinit var postsListFragment: PostsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postsListViewModel = ViewModelProviders.of(this).get(PostsListViewModel::class.java)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        navigationView = findViewById<NavigationView>(R.id.drawer_menu)
        navigationView.setNavigationItemSelectedListener(this)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        postsListFragment = PostsListFragment()

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, postsListFragment)
                .commit()

            navigationView.setCheckedItem(R.id.nav_home)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_home -> {
                postsListViewModel.fetchNewPosts()
                showFragment(postsListFragment)
            }
            R.id.nav_sale ->{
                postsListViewModel.getFavoritePosts("Vente")
                showFragment(postsListFragment)
//                navigationView.setCheckedItem(R.id.nav_sale)
            }
            R.id.nav_location -> {
                postsListViewModel.getFavoritePosts("Location")
                showFragment(postsListFragment)
//                navigationView.setCheckedItem(R.id.nav_location)
            }

            R.id.nav_holiday -> {
                postsListViewModel.getFavoritePosts("Location vacance")
                showFragment(postsListFragment)
//                navigationView.setCheckedItem(R.id.nav_holiday)
            }

            R.id.nav_subscribe -> showFragment(WilayaSubscriptionFragment())
        }
        navigationView.setCheckedItem(item.itemId)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}
