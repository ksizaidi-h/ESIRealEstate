package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class WilayaSubscriptionViewModel(application: Application) :AndroidViewModel(application) {
    val wilayaSubscriber = WilayaSubscriber(application.applicationContext)
    val subscribedWilayas = wilayaSubscriber.subscribedWilayas.map { it.wilayaName }

    fun subscribeToWilaya(wilaya : String){
        wilayaSubscriber.subscribeToWilaya(wilaya)
    }

    fun unSubScribeFromWilaya(wilaya : String){
        wilayaSubscriber.unsubscribeFromWilaya(wilaya)
    }
}