package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.excptions

import android.app.Application
import android.content.Context
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R

class NotConnectedException(val context: Context) : Exception(){
    override val message: String?
        get() = context.getString(R.string.not_connected_message)
}