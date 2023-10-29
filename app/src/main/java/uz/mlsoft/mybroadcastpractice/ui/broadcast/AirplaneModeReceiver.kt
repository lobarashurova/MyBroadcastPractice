package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class AirplaneModeReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningAirplaneModeReceiver: ((String) -> Unit)? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (mySharedPref.airPlaneMode) {
            if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                val isAirPlaneModeOn = Settings.Global.getInt(
                    context.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON,
                    0
                ) != 0

                if (isAirPlaneModeOn) {
                    listeningAirplaneModeReceiver?.invoke("Airplane mode is on")
                } else {
                    listeningAirplaneModeReceiver?.invoke("Airplane mode is off")
                }
            }
        }
    }

    fun setAirplaneModeReceiver(block: (String) -> Unit) {
        this.listeningAirplaneModeReceiver = block
    }
}