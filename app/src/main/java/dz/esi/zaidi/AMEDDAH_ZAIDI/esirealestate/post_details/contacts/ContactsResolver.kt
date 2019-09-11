package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.util.CursorUtil.getColumnIndex



class ContactsResolver(val context: Context) {

    private val TAG = "ContactsResolver"

    fun getContacts() : List<Contact>{
        val contacts = ArrayList<Contact>()
        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null)
        while (cursor!!.moveToNext()){
           val id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
            Log.d(TAG, "getContacts : id = $id")
           val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            Log.d(TAG, "getContacts : name = $name")
            val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d(TAG, "getContacts : number = $number")

            val emails = context.contentResolver.query(
                Email.CONTENT_URI,
                null,
                Email.CONTACT_ID + " = " + id,
                null,
                null
            )
            var email: String? = null
            if (emails?.moveToFirst()!!){
                email = emails.getString(emails.getColumnIndex(Email.DATA))
                Log.d(TAG,"getContacts : $email")
            }
            emails.close()

            contacts.add(Contact(id.toInt(),number,name,email))
        }
        cursor.close()
        return listOf(*contacts.toTypedArray())
    }
}