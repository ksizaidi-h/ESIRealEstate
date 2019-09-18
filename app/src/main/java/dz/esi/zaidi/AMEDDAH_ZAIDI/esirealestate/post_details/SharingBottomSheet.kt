package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.ContactsChoose
import kotlinx.android.synthetic.main.share_dialog.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class SharingBottomSheet(val link : String) : BottomSheetDialogFragment() {

    companion object{
        const val SEND_MESSAGES_REQUEST_CODE = 0
        const val SEND_EMAILS_REQUEST_CODE = 1
        private const val READ_CONTACTS_AND_SEND_SMS = 50
        private const val READ_CONTACTS_AND_GET_ACCOUNTS = 51
        private const val REQUEST_ACCOUNT_PICKER  = 5
        const val GRANT_SEND_PERMISSION_CODE = 200
        private const val TAG = "SharingBottomSheet"
    }

    private lateinit var sharingViewModel: SharingPostViewModel
    private lateinit var mCredential: GoogleAccountCredential

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
            sendSms()
            this.dismiss()
        }

        view?.btn_email?.setOnClickListener {
            sendEmails()
            this.dismiss()

        }

        super.onActivityCreated(savedInstanceState)
    }

    @AfterPermissionGranted(READ_CONTACTS_AND_SEND_SMS)
    private fun sendSms(){
        val perms = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS)
        if(EasyPermissions.hasPermissions(activity!!, *perms)){
            Log.d(TAG,"sendSms")
            chooseContacts(SEND_MESSAGES_REQUEST_CODE)
        }else{
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(activity!!, READ_CONTACTS_AND_SEND_SMS ,*perms)
                    .setRationale(R.string.sms_rational)
                    .setPositiveButtonText(R.string.allow)
                    .setNegativeButtonText(R.string.deny)
                    .build())
        }
    }

    @AfterPermissionGranted(READ_CONTACTS_AND_GET_ACCOUNTS)
    private fun sendEmails(){
        val perms = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(EasyPermissions.hasPermissions(activity!!, *perms)){
            Log.d(TAG,"sendEmails")
            chooseContacts(SEND_EMAILS_REQUEST_CODE)
        }else{
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(activity!!, READ_CONTACTS_AND_GET_ACCOUNTS ,*perms)
                    .setRationale(R.string.sms_rational)
                    .setPositiveButtonText(R.string.allow)
                    .setNegativeButtonText(R.string.deny)
                    .build())
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)  {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,activity)
    }

    fun chooseContacts(requestCode : Int){
        val intent = if(requestCode == SEND_EMAILS_REQUEST_CODE) {
            ContactsChoose.getIntent(activity!!,true,link)
        }else{
            ContactsChoose.getIntent(activity!!,false,link)
        }
        startActivity(intent)
    }



}