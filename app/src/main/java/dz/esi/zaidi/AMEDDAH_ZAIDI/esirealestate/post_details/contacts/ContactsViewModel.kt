package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.app.Application
import android.content.res.TypedArray
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.Utils
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
    val isListEmpty : LiveData<Boolean> = MutableLiveData()
    val isOnline : LiveData<Boolean> = MutableLiveData()

    private val connectionState = isOnline as MutableLiveData
    private val listState = isListEmpty as MutableLiveData

    companion object{
        private const val TAG = "ContactsViewModel"
    }


    init {
        val resolver = ContactsResolver(context)
        contacts = HashSet(resolver.getContacts())
        (isListEmpty as MutableLiveData).value = true
    }

    fun addContactToChosen(contact: Contact){
        chosenContacts.add(contact)
        if (chosenContacts.isNotEmpty()){
            listState.value = false
        }
    }

    fun removeContactFromChosen(contact: Contact){
        chosenContacts.remove(contact)
        if (chosenContacts.isEmpty()){
            listState.value = true
        }
    }

    fun getCurrentChosen() : List<Contact>{
        return chosenContacts.toList()
    }

    fun sendEmails(link : String){
        connectionState.postValue(Utils.isInternetAvailable(context))
        if(connectionState.value == true){
            val emails = chosenContacts.map { it.email!! }.toList()
            mailSender.sendEmails(emails, context.getString(R.string.message_body, link))
            Log.i(TAG,"sendEmails $emails")

        }
    }

    fun sendMessages(link : String){
        val phoneNumbers = chosenContacts.map { it.phone }
        smsSender.sendMessages(phoneNumbers, context.getString(R.string.message_body,link))
        Log.i(TAG,"sendMessages $phoneNumbers")
    }


}