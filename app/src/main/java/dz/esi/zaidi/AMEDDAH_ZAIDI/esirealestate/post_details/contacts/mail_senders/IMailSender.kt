package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.mail_senders

interface IMailSender {
    fun sendEmails(emails : List<String>, message : String)
}