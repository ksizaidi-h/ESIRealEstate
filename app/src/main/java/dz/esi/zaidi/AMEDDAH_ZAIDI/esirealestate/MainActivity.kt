package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks.UserLoginFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.*
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service.WilayaSubscriptionFragment

//import java.util.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    private lateinit var postsListViewModel : PostsListViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView : NavigationView
    private lateinit var postsListFragment: PostsListFragment
    private val favoriteFragment = FavoriteFragment()
    private lateinit var favoritesViewModel : FavoritesViewModel
    private val viewStack = ViewStack()
    private var currentView = HOME

    companion object{
        private const val HOME = "Home"
        private const val SELL = "Sell"
        private const val RENT = "Rent"
        private const val HOLIDAY = "Holiday"
        private const val BOOKMARK = "Bookmark"
        private const val SELL_CATEGORY = "Vente"
        private const val RENT_CATEGORY = "Location"
        private const val HOLIDAY_CATEGORY = "Location vacance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postsListViewModel = ViewModelProviders.of(this).get(PostsListViewModel::class.java)
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

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
            supportActionBar?.title = getString(R.string.home)
        }
    }



    private fun showView(view : String){
        when(view){
            HOME -> {
                supportActionBar?.title = getString(R.string.home)
                postsListViewModel.fetchNewPosts()
                showFragment(postsListFragment)
                navigationView.setCheckedItem(R.id.nav_home)
            }

            SELL -> {
                supportActionBar?.title = getString(R.string.sale)
                favoritesViewModel.getFavoritePosts(SELL_CATEGORY)
                showFragment(favoriteFragment)
                    navigationView.setCheckedItem(R.id.nav_sale)

            }

            RENT -> {
                supportActionBar?.title = getString(R.string.nav_location)
                favoritesViewModel.getFavoritePosts(RENT_CATEGORY)
                showFragment(favoriteFragment)
                navigationView.setCheckedItem(R.id.nav_rent)

            }

            HOLIDAY -> {
                supportActionBar?.title = getString(R.string.holiday)
                favoritesViewModel.getFavoritePosts(HOLIDAY_CATEGORY)
                showFragment(favoriteFragment)
                navigationView.setCheckedItem(R.id.nav_holiday)
            }

        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_home -> {
                showView(HOME)
                changeCurrentView(HOME)
            }
            R.id.nav_sale ->{
                showView(SELL)
                changeCurrentView(SELL)

            }
            R.id.nav_rent -> {
                showView(RENT)
                changeCurrentView(RENT)

            }

            R.id.nav_holiday -> {
                showView(HOLIDAY)
                changeCurrentView(HOLIDAY)
            }

            R.id.nav_subscribe -> {
                if(viewStack.isEmpty()) viewStack.push(HOME)
                showFragment(WilayaSubscriptionFragment())
                navigationView.setCheckedItem(item.itemId)
                supportActionBar?.title = getString(R.string.subscribe)

            }

            R.id.nav_bookmark -> {
                val intent = Intent(this, UserLoginFragment::class.java)
                startActivity(intent)
            }

        }
        Log.d("viewStack", viewStack.toString())
        navigationView.setCheckedItem(item.itemId)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeCurrentView(view : String){
        if(currentView != view){
            if(viewStack.isEmpty()) {
                viewStack.push(HOME)
            }else{
                viewStack.push(currentView)
            }
            currentView = view
        }
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else if ((viewStack.isEmpty())) {
            super.onBackPressed()
        }else{
            showView(viewStack.pop())
            Log.d("viewStack", viewStack.toString())
        }
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }




}
