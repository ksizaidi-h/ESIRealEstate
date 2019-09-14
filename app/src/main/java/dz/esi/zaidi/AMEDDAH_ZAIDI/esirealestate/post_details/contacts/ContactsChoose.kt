package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlin.collections.HashSet

class ContactsChoose : AppCompatActivity(){
    private var forEmails = false

    companion object{
        private const val TAG = "ContactsChoose"
        private const val EMAILS_ONLY = "emailsOnly"

        const val CHOSEN_CONTACTS_EXTRA = "chosenContactsExtra"

        fun getIntent(context: Context, emailsOnly : Boolean) : Intent{
            val intent = Intent(context, ContactsChoose::class.java)
            intent.putExtra(EMAILS_ONLY,emailsOnly)
            return intent
        }
    }
    private val adapter = ContactsAdapter()
    private lateinit var contactsViewModel : ContactsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        contactsViewModel = ViewModelProviders.of(this  ).get(ContactsViewModel::class.java)

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

        btn_send.setOnClickListener{
            val contactsIntent = Intent()

            val chosenContacts : Array<String> = if (forEmails){
                contactsViewModel.getCurrentChosen().map { it.email!! }.toTypedArray()
            }else{
                contactsViewModel.getCurrentChosen().map { it.phone }.toTypedArray()
            }
            contactsIntent.putExtra(CHOSEN_CONTACTS_EXTRA, chosenContacts)
            setResult(Activity.RESULT_OK, contactsIntent)
            Log.d(TAG,"before finish")
            finish()
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

}