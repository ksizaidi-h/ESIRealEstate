package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.util.Log
import androidx.lifecycle.ViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders.ISmsSender

class SharingPostViewModel : ViewModel() {

    lateinit var smsSender : ISmsSender

    companion object{
        private const val TAG = "SharingPostViewModel"
    }
    fun sendEmails(emails : List<String>, message : String){
        Log.i(TAG,"sendEmails $emails")
    }

    fun sendMessages(phoneNumbers : List<String>, message : String){
        smsSender.sendMessages(phoneNumbers, message)
        Log.i(TAG,"sendMessages $phoneNumbers")
    }
}