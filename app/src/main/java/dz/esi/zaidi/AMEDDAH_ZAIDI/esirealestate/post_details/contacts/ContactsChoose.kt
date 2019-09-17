package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.mail_senders.GmailClientMailSender
import kotlinx.android.synthetic.main.activity_contacts.*
import org.jetbrains.anko.doAsync
import java.lang.Exception
import kotlin.collections.HashSet

class ContactsChoose : AppCompatActivity(){
    private var forEmails = false

    companion object{
        private const val TAG = "ContactsChoose"
        private const val EMAILS_ONLY = "emailsOnly"
        private const val LINK_EXTRA = "linkExtra"

        const val CHOSEN_CONTACTS_EXTRA = "chosenContactsExtra"
        private const val GRANT_SEND_MAIL_PERMISSION = 200

        fun getIntent(context: Context, emailsOnly : Boolean, link : String) : Intent{
            val intent = Intent(context, ContactsChoose::class.java)
            intent.putExtra(EMAILS_ONLY,emailsOnly)
            intent.putExtra(LINK_EXTRA,link)
            return intent
        }
    }
    private val adapter = ContactsAdapter()
    private lateinit var contactsViewModel : ContactsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        contactsViewModel = ViewModelProviders.of(this  ).get(ContactsViewModel::class.java)
        contactsViewModel.mailSender = GmailClientMailSender(this)
        rv_contacts.layoutManager = LinearLayoutManager(this)
        rv_contacts.setHasFixedSize(true)
        adapter.chosenContacts = HashSet(contactsViewModel.getCurrentChosen())
        rv_contacts.adapter = adapter

        var contacts = contactsViewModel.contacts.toList()
        if(intent.hasExtra(EMAILS_ONLY)){
            if (intent.getBooleanExtra(EMAILS_ONLY,false)){
                forEmails = true
                contacts = contacts.filter { it.email != null }
            }
        }

        adapter.submitList(contacts)
        adapter.onAddButtonClickListener = onAddButtonClickListener
        val link = intent.getStringExtra(LINK_EXTRA)
        btn_send.setOnClickListener{
            doAsync {
                try {
                    contactsViewModel.sendEmails(link!!)
                }catch (e : Exception){
                    when(e){
                        is UserRecoverableAuthIOException -> {
                            startActivityForResult(e.intent, GRANT_SEND_MAIL_PERMISSION)
                        }
                        else ->{
                            Log.d(TAG, "exception : $e / message : ${e.message} / cause : ${e.cause} ")
                        }
                    }
                }
            }


            Log.d(TAG,"before finish")
        //    finish()
        }

    }

    val onAddButtonClickListener = object : ContactsAdapter.OnAddButtonClickListener{
        override fun addContactToList(contact: Contact) {
            contactsViewModel.addContactToChosen(contact)
            adapter.chosenContacts = HashSet(contactsViewModel.getCurrentChosen())
            Log.i(TAG,"chosenContacts : ${contactsViewModel.getCurrentChosen()}")
        }

        override fun removeContactFromList(contact: Contact) {
            contactsViewModel.removeContactFromChosen(contact)
            adapter.chosenContacts = HashSet(contactsViewModel.getCurrentChosen())
            Log.i(TAG,"chosenContacts : ${contactsViewModel.getCurrentChosen()}")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){

        }
    }

}