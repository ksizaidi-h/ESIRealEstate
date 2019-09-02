package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.util.Log
import java.util.*

class ViewStack : Stack<String>() {
    override fun push(item: String?): String? {
        if (search(item) != -1){
            remove(item)
        }
        return super.push(item)
    }


}