package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.app.Application
import android.content.res.TypedArray
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class ContactsViewModel(application: Application) : AndroidViewModel(application){

    val contacts : Set<Contact>

    private val chosenContacts = HashSet<Contact>()
    private val context = application.applicationContext

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


}