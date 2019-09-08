package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.subscription_service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WilayaSubscriber(val context: Context) {

    val wilayas = MutableLiveData<List<String>>()
    var subscribedWilayas = getSubScribedWilays()
        private set

    companion object{
        private const val SHARED_PREFERENCES = "ESIRealEstate"
        private const val WILAYAS = "wilayas"
    }

    init {
        wilayas.value = subscribedWilayas.map { it.wilayaName }
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)


    fun subscribeToWilaya(wilaya : String){
        subscribedWilayas.add(WilayaPreference(wilaya, null))
        saveChanges()
       }

    fun unsubscribeFromWilaya(wilaya: String){
        subscribedWilayas.remove(subscribedWilayas.first { it.wilayaName == wilaya })
        saveChanges()
    }

    fun getSubScribedWilays() : ArrayList<WilayaPreference>{
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val gson = Gson()
        val savedWilayas = sharedPreferences.getString(WILAYAS, null)
        var wilayasList = ArrayList<WilayaPreference>()
        if (savedWilayas != null){
            wilayasList = gson.fromJson<ArrayList<WilayaPreference>>(savedWilayas)
        }
        return wilayasList
    }


     fun saveChanges(){
         wilayas.value = subscribedWilayas.map { it.wilayaName }
        val savedWilayas =  Gson().toJson(subscribedWilayas)
        val editor = context.getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE).edit()
        editor.putString(WILAYAS, savedWilayas)
        editor.apply()

    }
}