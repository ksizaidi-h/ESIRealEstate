package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.PostsListFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.PostsListViewModel
//import java.util.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    private lateinit var postsListViewModel : PostsListViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView : NavigationView
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

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PostsListFragment())
                .commit()

            navigationView.setCheckedItem(R.id.nav_home)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_home -> {
                postsListViewModel.fetchNewPosts()
            }
            R.id.nav_sale ->{
                postsListViewModel.getFavoritePosts("Vente")
            }
            R.id.nav_location -> {
                postsListViewModel.getFavoritePosts("Location")
            }

            R.id.nav_holiday -> {
                postsListViewModel.getFavoritePosts("Location vacance")
            }
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

    private val navListener = object: NavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var selectedFragment : Fragment? = null

            when(item.itemId){
                R.id.nav_home -> {
                    selectedFragment = PostsListFragment()
                }


            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment!!)
                .commit()
            return true
        }

    }
}
