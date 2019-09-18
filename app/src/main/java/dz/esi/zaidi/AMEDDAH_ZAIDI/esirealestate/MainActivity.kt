package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.app.Activity
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks.BookMarksViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks.BookmarksFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks.LoginActivity
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.home.*
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service.WilayaSubscriptionFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.users.ProfileFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.users.User
import kotlinx.android.synthetic.main.nav_header.view.*

//import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var postsListViewModel: PostsListViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var postsListFragment: PostsListFragment
    private val favoriteFragment = FavoriteFragment()
    private lateinit var favoritesViewModel: FavoritesViewModel
    private val viewStack = ViewStack()
    private var currentView = HOME
    private lateinit var bookMarksViewModel: BookMarksViewModel

    companion object {
        private const val HOME = "Home"
        private const val SELL = "Sell"
        private const val RENT = "Rent"
        private const val HOLIDAY = "Holiday"
        private const val BOOKMARK = "Bookmark"
        private const val SELL_CATEGORY = "Vente"
        private const val RENT_CATEGORY = "Location"
        private const val HOLIDAY_CATEGORY = "Location vacance"
        const val PROFILE_REQUEST = 78
        const val BOOKMARK_REQUEST = 97

        private const val TAG = "MainActivity : header"
    }

    private val categoriesMap =
        hashMapOf(SELL to SELL_CATEGORY, RENT to RENT_CATEGORY, HOLIDAY to HOLIDAY_CATEGORY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        postsListViewModel = ViewModelProviders.of(this).get(PostsListViewModel::class.java)
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        bookMarksViewModel = ViewModelProviders.of(this).get(BookMarksViewModel::class.java)

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

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, postsListFragment)
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
            supportActionBar?.title = getString(R.string.home)
        }


    }

    override fun onResume() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val navigationHeader = navigationView.getHeaderView(0)
        if (auth.uid.toString() != "null") {
            User.isLoggedIn = true
            db.collection("users").document(auth.uid!!).get()
                .addOnSuccessListener { task ->
                    User.links =
                        if (task.get("bookmarks") == null) ArrayList<String>() else task.get("bookmarks") as ArrayList<String>
                    val email = auth.currentUser!!.email
                    val name = auth.currentUser!!.displayName
                    val photo = auth.currentUser!!.photoUrl

                    User.email = email!!
                    User.isLoggedIn = true
                    User.name = name.toString()
                    User.urlPhoto = photo

                    navigationHeader.tv_user_email.text = User.email
                    navigationHeader.tv_user_name.text = User.name

                    Picasso.get().load(User.urlPhoto) to navigationHeader.iv_user_photo

                }
                .addOnFailureListener { e ->
                    Log.d("Error adding document", "error")
                }
        }
        favoritesViewModel.updateFavorites()
        // bookMarksViewModel.refreshBookMarks()
        super.onResume()
    }


    private fun showView(view: String) {
        when (view) {
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

            BOOKMARK -> {
                supportActionBar?.title = getString(R.string.my_bookmarks)
                bookMarksViewModel.getBookMarkedPosts()
                showFragment(BookmarksFragment())
                navigationView.setCheckedItem(R.id.nav_bookmark)

            }

        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_home -> {
                showView(HOME)
                changeCurrentView(HOME)
            }
            R.id.nav_sale -> {
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
                if (viewStack.isEmpty()) viewStack.push(HOME)
                showFragment(WilayaSubscriptionFragment())
                navigationView.setCheckedItem(item.itemId)
                supportActionBar?.title = getString(R.string.subscribe)

            }

            R.id.nav_bookmark -> {
                if (User.isLoggedIn) {
                    showView(BOOKMARK)
                    changeCurrentView(BOOKMARK)
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivityForResult(intent, BOOKMARK_REQUEST)
                }
            }

            R.id.nav_profile -> {
                if (User.isLoggedIn) {
                    if (viewStack.isEmpty()) viewStack.push(HOME)
                    showFragment(ProfileFragment())
                    navigationView.setCheckedItem(item.itemId)
                    supportActionBar?.title = getString(R.string.profile)
                } else {

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivityForResult(intent, MainActivity.PROFILE_REQUEST)

                }

            }

        }
        Log.d("viewStack", viewStack.toString())
        Log.d(TAG,"currentView : $currentView")
        navigationView.setCheckedItem(item.itemId)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeCurrentView(view: String) {
        if (currentView != view) {
            if (viewStack.isEmpty()) {
                viewStack.push(HOME)
            } else {
                viewStack.push(currentView)
            }
            currentView = view
        }
    }

    override fun onBackPressed() {
        Log.d(TAG,"backButtonPressed")
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else if ((viewStack.isEmpty())) {
            super.onBackPressed()
        } else {
            showView(viewStack.pop())
            Log.d("viewStack", viewStack.toString())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                PROFILE_REQUEST -> {
                    if (viewStack.isEmpty()) viewStack.push(HOME)
                    showFragment(ProfileFragment())
                    navigationView.setCheckedItem(R.id.nav_profile)
                    supportActionBar?.title = getString(R.string.profile)
                }

                BOOKMARK_REQUEST ->{
                    showView(BOOKMARK)
                }
            }
        }else{
            showView(HOME)
        }

    }


}
