package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.mail_senders

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.Base64
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.User
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.excptions.NotConnectedException
import java.io.IOException
import java.io.ByteArrayOutputStream
import java.util.*
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class GmailClientMailSender(val context: Context) : IMailSender {


    companion object {
        private const val TAG = "GmailClientMailSender"
        val SCOPES = arrayOf(
            // GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_COMPOSE
//            GmailScopes.GMAIL_INSERT,
//            GmailScopes.GMAIL_MODIFY,
//            GmailScopes.GMAIL_READONLY,
//            GmailScopes.MAIL_GOOGLE_COM
        )
    }


    private val isSending = MutableLiveData<Boolean>()
    override fun getSendingState(): LiveData<Boolean> = isSending

    val mCredential = GoogleAccountCredential.usingOAuth2(
        context, listOf(*SCOPES)
    ).setBackOff(ExponentialBackOff())


    // Method to send email
    @Throws(MessagingException::class, IOException::class)
    private fun sendMessage(service: Gmail, userId: String, email: MimeMessage): String {
        var message = createMessageWithEmail(email)
        // GMail's official method to send email with oauth2.0
        message = service.users().messages().send(userId, message).execute()
        return message.getId()
    }

    @Throws(MessagingException::class)
    private fun createEmail(
        to: String,
        from: String,
        subject: String,
        bodyText: String
    ): MimeMessage {
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)

        val email = MimeMessage(session)
        val tAddress = InternetAddress(to)
        val fAddress = InternetAddress(from)

        email.setFrom(fAddress)
        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress)
        email.setSubject(subject)

        // Changed for adding attachment and text
        // This line is used for sending only text messages through mail
        email.setText(bodyText);

        return email
    }

    @Throws(MessagingException::class, IOException::class)
    private fun createMessageWithEmail(email: MimeMessage): Message {
        val bytes = ByteArrayOutputStream()
        email.writeTo(bytes)
        val encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray())
        val message = Message()
        message.setRaw(encodedEmail)
        return message
    }

    @Throws(NotConnectedException::class)
    override fun sendEmails(emails: List<String>, message: String) {
        if (!User.isLoggedIn) {
            throw NotConnectedException(context)
        } else {
            val senderEmail = "me"
            mCredential.setSelectedAccountName(User.email)
            val service = Gmail.Builder(
                AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(),
                mCredential
            )
                .setApplicationName(context.resources.getString(R.string.app_name))
                .build()
            isSending.postValue(true)
            for (i in 0..15) {
                val email = createEmail(
                    "hamza.kmsi@gmail.com",
                    senderEmail,
                    context.getString(R.string.mail_subject),
                    message
                )
                val response = sendMessage(service, senderEmail, email)
                Log.d(TAG, "response : $response")

            }
            isSending.postValue(false)
        }
    }
}


