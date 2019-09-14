package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders

import android.telephony.SmsManager

class DefaultSmsSender : ISmsSender {

    private val smsManager = SmsManager.getDefault()
    override fun sendMessages(phoneNumbers: List<String>, message : String) {
        for (phoneNumber in phoneNumbers){
            smsManager.sendTextMessage(phoneNumber, null,message, null,null)
        }
    }
}