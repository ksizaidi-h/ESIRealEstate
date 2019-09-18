package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders

import android.telephony.SmsManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DefaultSmsSender : ISmsSender {

    private val sendingState = MutableLiveData<Boolean>()
    private val smsManager = SmsManager.getDefault()
    override fun sendMessages(phoneNumbers: List<String>, message : String) {
        sendingState.postValue(true)
        for (phoneNumber in phoneNumbers){
            smsManager.sendTextMessage(phoneNumber, null,message, null,null)
        }
        sendingState.postValue(false)
    }

    override fun getSendingState(): LiveData<Boolean> = sendingState
}