package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class FlashLightReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningCharger:((String)->Unit)?=null
    override fun onReceive(context: Context, intent: Intent) {
        myLog("power mode receiver")
        if (mySharedPref.chargerMode) {
            if (intent.action == Intent.ACTION_POWER_CONNECTED) {
                listeningCharger?.invoke("phone is connected to charging")
            } else if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
                listeningCharger?.invoke("phone is disconnected to charging")
            }
        }

    }

    fun setListeningCharger(block:(String)->Unit){
        this.listeningCharger=block
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun myLog(message: String) {
    Log.d("TTT", message)
}