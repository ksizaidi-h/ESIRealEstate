package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders

import androidx.lifecycle.LiveData

interface ISmsSender {
    fun sendMessages(phoneNumbers : List<String>, message : String)
    fun getSendingState() : LiveData<Boolean>
}