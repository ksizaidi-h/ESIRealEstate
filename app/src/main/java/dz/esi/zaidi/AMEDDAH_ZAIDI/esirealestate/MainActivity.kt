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
import java.util.*

//import java.util.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    private lateinit var postsListViewModel : PostsListViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView : NavigationView
    private lateinit var postsListFragment: PostsListFragment
    private val viewStack = Stack<Int>()
    companion object{
        private const val HOME = R.id.nav_home
        private const val SELL = R.id.nav_sale
        private const val RENT = R.id.nav_location
        private const val HOLIDAY = R.id.nav_holiday
        private const val SELL_CATEGORY = "Vente"
        private const val RENT_CATEGORY = "Location"
        private const val HOLIDAY_CATEGORY = "Location vacance"
    }

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
            viewStack.push(HOME)
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_home -> {
                showView(HOME)
            }
            R.id.nav_sale ->{
                showView(SELL)
            }
            R.id.nav_location -> {
                showView(RENT)
            }

            R.id.nav_holiday -> {
                showView(HOLIDAY)
            }

            R.id.nav_subscribe -> {
                showFragment(WilayaSubscriptionFragment())
                navigationView.setCheckedItem(item.itemId)
            }

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else if ((viewStack.isEmpty())) {
            super.onBackPressed()
        }else{
            showView(viewStack.pop())
        }
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showView(view : Int){
        when(view){
            HOME -> {
                postsListViewModel.fetchNewPosts()
            }

            SELL -> {
                postsListViewModel.getFavoritePosts(SELL_CATEGORY)
            }

            RENT -> {
                postsListViewModel.getFavoritePosts(RENT_CATEGORY)
            }

            HOLIDAY -> {
                postsListViewModel.getFavoritePosts(HOLIDAY_CATEGORY)
            }

        }

        if(!viewStack.isEmpty()){
            if(viewStack.peek() != view){
                viewStack.push(view)
            }
        }
        showFragment(postsListFragment)
        navigationView.setCheckedItem(view)

    }


}
