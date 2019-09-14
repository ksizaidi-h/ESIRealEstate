package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.mail_senders.IMailSender
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders.ISmsSender

class SharingPostViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var smsSender : ISmsSender
    lateinit var mailSender : IMailSender

    companion object{
        private const val TAG = "SharingPostViewModel"
    }
    fun sendEmails(emails : List<String>, message : String){
        mailSender.sendEmails(emails,message)
        Log.i(TAG,"sendEmails $emails")
    }

    fun sendMessages(phoneNumbers : List<String>, message : String){
        smsSender.sendMessages(phoneNumbers, message)
        Log.i(TAG,"sendMessages $phoneNumbers")
    }
}