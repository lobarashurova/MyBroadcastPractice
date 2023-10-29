package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class ScreenOrientationReceiver constructor(
    private val orientation: Int,
    private val mySharedPref: SharedPref
) : BroadcastReceiver() {
    private var listeningScreen: ((String) -> Unit)? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (mySharedPref.screenOrientationLocked) {
            if (intent?.action == Intent.ACTION_CONFIGURATION_CHANGED) {
                val isOrientationLocked = orientation == Configuration.ORIENTATION_LANDSCAPE ||
                        orientation == Configuration.ORIENTATION_PORTRAIT
                // Handle the screen orientation lock state here
                if (isOrientationLocked) {
                    listeningScreen?.invoke("Screen orientation is locked")
                    // Screen orientation is locked
                } else {
                    listeningScreen?.invoke("Screen orientation is not locked")
                    // Screen orientation is not locked
                }
            }
        }
    }


    fun setScreenListener(block: (String) -> Unit) {
        this.listeningScreen = block
    }
}