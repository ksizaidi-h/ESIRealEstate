package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.contact_fragment.view.*

class ContactFragment : Fragment() {

    companion object{
        const val PHONE = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.posts_details.ContactFragment.Phone"

        fun newInstance(phone : String) : ContactFragment{
            val fragment = ContactFragment()
            val args = Bundle()

            args.putString(PHONE, phone)

            fragment.arguments = args
            return fragment
        }
    }

    private var phone = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.contact_fragment,container,false)

        if(arguments != null){
            phone = arguments?.getString(PHONE)!!
        }
        v.tv_phone_number.text = context?.getString(R.string.phone_number,phone)
        v.btn_call.setOnClickListener {
            checkPermission()
        }

        return v
    }

    fun checkPermission() {
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
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone))
        startActivity(intent)
    }
}