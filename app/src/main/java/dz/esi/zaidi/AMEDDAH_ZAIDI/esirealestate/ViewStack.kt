package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.util.Log
import java.util.*

class ViewStack : Stack<String>() {
    override fun push(item: String?): String? {
        return if(search(item) == -1){
            Log.d("ViewStack : push $item","PUSH")
            super.push(item)
        }else{
            Log.d("ViewStack : push $item","EXISTS")
            null
        }
    }


}