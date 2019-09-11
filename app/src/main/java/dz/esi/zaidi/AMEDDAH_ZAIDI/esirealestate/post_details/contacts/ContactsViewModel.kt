package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.app.Application
import android.content.res.TypedArray
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class ContactsViewModel(application: Application) : AndroidViewModel(application){

    private lateinit var contacts : List<Contact>
    private val chosenContacts = ArrayList<Contact>()
    private val context = application.applicationContext

    init {
        val resolver = ContactsResolver(context)
        contacts = resolver.getContacts()
    }

    fun addContactToChosen(contact: Contact){
        chosenContacts.add(contact)
    }

    fun removeContactFromChosen(contact: Contact){
        chosenContacts.remove(contact)
    }

    fun getCurrentChosen() : Array<Contact>{
        return chosenContacts.toTypedArray()
    }


}