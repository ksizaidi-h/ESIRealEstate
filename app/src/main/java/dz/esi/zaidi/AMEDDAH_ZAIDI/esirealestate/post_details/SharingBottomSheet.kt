package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class SharingBottomSheet(val link : String) : BottomSheetDialogFragment() {

    companion object{
        const val SEND_MESSAGES_REQUEST_CODE = 0
        const val SEND_EMAILS_REQUEST_CODE = 1
        private const val READ_CONTACTS_AND_SEND_SMS = 50
        private const val TAG = "SharingBottomSheet"
    }

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
            sendSms()
            //this.dismiss()
        }

        view?.btn_email?.setOnClickListener {
            sendEmails()
            //this.dismiss()

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

    private fun sendEmails(){
        chooseContacts(SEND_EMAILS_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)  {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG,"onActivityResult : $resultCode")
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            val chosenContacts = data?.getStringArrayExtra(ContactsChoose.CHOSEN_CONTACTS_EXTRA)
            if (requestCode == SEND_EMAILS_REQUEST_CODE){
                sharingViewModel.sendEmails(chosenContacts!!.toList(), getString(R.string.message_body,link))
            }else {
                sharingViewModel.sendMessages(chosenContacts!!.toList(),getString(R.string.message_body,link))
            }
            this.dismiss()
        }

    }

    fun chooseContacts(requestCode : Int){
        val intent = if(requestCode == SEND_EMAILS_REQUEST_CODE) {
            ContactsChoose.getIntent(activity!!,true)
        }else{
            ContactsChoose.getIntent(activity!!,false)
        }
        startActivityForResult(intent, requestCode)
    }



}