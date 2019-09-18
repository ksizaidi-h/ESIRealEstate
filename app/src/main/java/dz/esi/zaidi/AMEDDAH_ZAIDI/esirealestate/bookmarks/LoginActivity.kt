package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.user.*
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.User





class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R.layout.user)

        // Button listeners
        btn_login_google.setOnClickListener(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        // [END config_signin

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        //TODO Check update UI

        updateUI(auth!!.uid)



    }
    // [END on_start_check_user]

    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val userId = auth.uid
                    val email = auth.currentUser!!.email
                    val name = auth.currentUser!!.displayName
                    val photo = auth.currentUser!!.photoUrl

                    User.email = email!!
                    User.isLoggedIn = true
                    User.name = name.toString()
                    User.urlPhoto = photo

                    db.collection("users").document(userId!!)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document.data != null) {
                                Log.d( "PPP",document.data.toString())
                                updateUI(userId)
                            } else {
                                val userDb = hashMapOf(
                                    "userId" to userId
                                )
                                db.collection("users").document(userId!!)
                                    .set(userDb)
                                    .addOnSuccessListener {
                                        Log.w("Aadding document", "User added to document fireStore")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }
                            }
                            Toast.makeText(this@LoginActivity, "Sign in success", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }

                    updateUI(userId)
                } else {
                    Toast.makeText(this@LoginActivity, "Sign in failed", Toast.LENGTH_SHORT).show()
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }

                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
    }

    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun signOut() {
        // Firebase sign out
        auth!!.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }


    private fun updateUI(user: String?) {
        /*
                if the user is already signed (currentUser.toString() != null) then hide btn_google_login and show related data
                else (currentUser.toString() == null ) leave the button after the user sign in successfully then hide btn_google_login and show related data
        */
        if (user.toString() != "null" ){
            // User already signed in Show bookmarks array and hide button
            db.collection("users").document(auth!!.uid!!).get()
                .addOnSuccessListener{ task ->
                   User.links =  if(task.get("bookmarks") == null) ArrayList<String>() else task.get("bookmarks" ) as ArrayList<String>
                    User.email = auth.currentUser?.email.toString()
                    setResult(Activity.RESULT_OK)
                    finish()

                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error adding document", e)
                }
        }
        else {
            // btn_google_login is already shown, the user should sign in I'll call the same function when the user successfully signed in
        }

        //Todo if you want to add sign out button the logic is ready just call signOut()
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R.id.btn_login_google -> signIn()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}