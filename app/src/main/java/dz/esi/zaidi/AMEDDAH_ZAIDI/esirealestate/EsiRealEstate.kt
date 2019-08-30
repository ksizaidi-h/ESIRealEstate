package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class EsiRealEstate : Application() {

    companion object{
        const val WILAYA_SUBSCRIPTION_CHANNEL = "wilayaSubscription"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                WILAYA_SUBSCRIPTION_CHANNEL,
                getString(R.string.subscribe),
                NotificationManager.IMPORTANCE_DEFAULT,
                getString(R.string.notification_description)
            )
        }
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
}