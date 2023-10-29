package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.speech.tts.TextToSpeech
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class LocationReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningLocation: ((String) -> Unit)? = null
    override fun onReceive(context: Context, intent: Intent) {
        myLog("onReceiver location ")
        if (mySharedPref.locationMode) {
            if (intent.action == LocationManager.MODE_CHANGED_ACTION) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isLocationEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (isLocationEnabled) {
                    listeningLocation?.invoke("Location is turned on")
                } else {
                    listeningLocation?.invoke("Location is turned off")
                }
            }
        }

    }

    fun setListeningLocation(block: (String) -> Unit) {
        this.listeningLocation = block
    }
}