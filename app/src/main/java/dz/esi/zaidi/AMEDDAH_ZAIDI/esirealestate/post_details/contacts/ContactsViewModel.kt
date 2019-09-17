package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.app.Application
import android.content.res.TypedArray
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.mail_senders.IMailSender
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders.ISmsSender
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class ContactsViewModel(application: Application) : AndroidViewModel(application){
    val contacts : Set<Contact>

    private val chosenContacts = HashSet<Contact>()
    private val context = application.applicationContext

    lateinit var smsSender : ISmsSender
    lateinit var mailSender : IMailSender

    companion object{
        private const val TAG = "ContactsViewModel"
    }


    init {
        val resolver = ContactsResolver(context)
        contacts = HashSet(resolver.getContacts())
    }

    fun addContactToChosen(contact: Contact){
        chosenContacts.add(contact)
    }

    fun removeContactFromChosen(contact: Contact){
        chosenContacts.remove(contact)
    }

    fun getCurrentChosen() : List<Contact>{
        return chosenContacts.toList()
    }

    fun sendEmails(link : String){
        val emails = chosenContacts.map { it.email!! }.toList()
        mailSender.sendEmails(emails, context.getString(R.string.message_body, link))
        Log.i(TAG,"sendEmails $emails")
    }
//
//    fun sendMessages(){
//        smsSender.sendMessages(phoneNumbers, message)
//        Log.i(TAG,"sendMessages $phoneNumbers")
//    }


}