package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.EsiRealEstate
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.Utils

class WildaSubscriptionServiceReceiver : BroadcastReceiver(){
    companion object{
        const val REQUEST_CODE = 3
    }

    override fun onReceive(context : Context, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            EsiRealEstate.scheduleNotification(context, System.currentTimeMillis())
        }else{

            if(Utils.isInternetAvailable(context)){
                val mIntent = Intent()
                WilayaSubscriptionService.enqueueWork(context,mIntent)
            }
        }
    }
}