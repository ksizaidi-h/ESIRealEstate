package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        view?.btn_message?.setOnClickListener {
            checkPermission(SEND_MESSAGES_REQUEST_CODE)
        }

        view?.btn_email?.setOnClickListener {
            checkPermission(SEND_EMAILS_REQUEST_CODE)
        }

        super.onActivityCreated(savedInstanceState)
    }


    fun checkPermission(requestCode : Int) {
        if (ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(activity!!)
                    .setTitle(getString(R.string.permission_required))
                    .setMessage("L'application a besion de cette permission pour effectuer des appels téléphoniques")
                    .setPositiveButton(getString(R.string.allow), object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            ActivityCompat.requestPermissions(activity!!,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                42)
                        }

                    })
                    .setNegativeButton(getString(R.string.deny), object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.dismiss()
                        }

                    })
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            chooseContacts(requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray)  {
        if ((requestCode == SEND_MESSAGES_REQUEST_CODE) or (requestCode == SEND_EMAILS_REQUEST_CODE)) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                chooseContacts(requestCode)
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun chooseContacts(requestCode : Int){
        val intent = Intent(activity,
            ContactsChoose::class.java)
        startActivityForResult(intent, requestCode)
    }


    interface SharingDialogListener {
        fun onMessagesClicked()
        fun onEmailClicked()
    }

}