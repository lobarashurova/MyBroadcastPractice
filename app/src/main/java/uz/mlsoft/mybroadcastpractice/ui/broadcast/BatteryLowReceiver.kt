package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class BatteryLowReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningBattery: ((String) -> Unit)? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            val batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPercentage = batteryLevel * 100 / batteryScale.toFloat()

            val isBatteryLow = batteryPercentage <= 15
            if (mySharedPref.batteryLow) {
                if (isBatteryLow) {
                    listeningBattery?.invoke("Your battery level is lower than 15%")
                } else {
                    listeningBattery?.invoke("Your battery level is higher than 15%")
                }
            }
        }
    }

    fun setBattery(block: ((String) -> Unit)) {
        this.listeningBattery = block
    }
}