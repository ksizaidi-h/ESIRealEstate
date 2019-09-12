package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service.WilayaSubscriptionServiceReceiver
import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.bookmarks.UserLoginFragment


class EsiRealEstate : Application() {

    companion object{
        const val WILAYA_SUBSCRIPTION_CHANNEL = "wilayaSubscription"
        const val SCHEDULE_WILAYA_SERVICE_ACTION = "dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.SCHEDULE_WILAYA_SERVICE_ACTION"
        private const val TAG = "EsiRealEstate"

        fun scheduleNotification(context: Context, first : Long){
            val intent = Intent(context, WilayaSubscriptionServiceReceiver::class.java)
            val pIntent = PendingIntent.getBroadcast(context,
                WilayaSubscriptionServiceReceiver.REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT)

            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val firstMillis = first
            val minutes : Long = 1
            val interval : Long = 60*1000*minutes
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis + interval,
                interval, pIntent)
        }
    }

    override fun onCreate() {
        super.onCreate()

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()

        if (auth.uid!!.toString() != "null"){
            User.isLoggedIn=true
            db.collection("users").document(auth!!.uid!!).get()
                .addOnSuccessListener{ task ->
                    User.links = task.get("bookmarks") as ArrayList<String>

                }
                .addOnFailureListener { e ->
                    Log.d("Error adding document", "error")
                }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                WILAYA_SUBSCRIPTION_CHANNEL,
                getString(R.string.subscribe),
                NotificationManager.IMPORTANCE_DEFAULT,
                getString(R.string.notification_description)
            )
        }
        val minutes:Long = 1
        val afterStartInterval :Long = 60*1000*minutes
        scheduleNotification(applicationContext, System.currentTimeMillis() + afterStartInterval)
       // cancelAlarm()

        Log.d(TAG ,"onCreate")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationChannelId : String,
                                          notificationName : String,
                                          notificationImportance : Int,
                                          notificationDescription : String){

            val notificationChannel = NotificationChannel(
                notificationChannelId,
                notificationName,
                notificationImportance
            )

            notificationChannel.description = notificationDescription
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)

    }



    fun cancelAlarm() {
        val intent = Intent(applicationContext, WilayaSubscriptionServiceReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(
            this, WilayaSubscriptionServiceReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pIntent)
    }

    override fun onTerminate() {
        Log.d(TAG , "onTerminate")
        super.onTerminate()
    }
}