package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.ContactsChoose
import kotlinx.android.synthetic.main.share_dialog.view.*

class SharingBottomSheet : BottomSheetDialogFragment() {

    companion object{
        const val SEND_MESSAGES_REQUEST_CODE = 0
        const val SEND_EMAILS_REQUEST_CODE = 1
    }

    lateinit var sharingDialogListener: SharingDialogListener
    private lateinit var sharingViewModel: SharingPostViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.share_dialog, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        sharingViewModel = ViewModelProviders.of(this).get(SharingPostViewModel::class.java)
        val intent = Intent(activity,
            ContactsChoose::class.java)
        view?.btn_message?.setOnClickListener {
            startActivityForResult(intent, SEND_MESSAGES_REQUEST_CODE)
        }

        view?.btn_email?.setOnClickListener {
            startActivityForResult(intent, SEND_EMAILS_REQUEST_CODE)
        }

        super.onActivityCreated(savedInstanceState)
    }

    interface SharingDialogListener {
        fun onMessagesClicked()
        fun onEmailClicked()
    }

}