package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.sms_senders

interface ISmsSender {
    fun sendMessages(phoneNumbers : List<String>, message : String)
}