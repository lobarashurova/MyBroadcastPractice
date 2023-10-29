package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class HeadPhonesReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningHeadPhones: ((String) -> Unit)? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (mySharedPref.headPhones) {
            if (intent.action == AudioManager.ACTION_HEADSET_PLUG) {
                val state = intent.getIntExtra("state", -1)
                val isHeadphonesConnected = state == 1
                if (isHeadphonesConnected) {
                    listeningHeadPhones?.invoke("Headphone is connected")
                } else {
                    listeningHeadPhones?.invoke("Headphone is disconnected")
                }
            }
        }
    }

    fun setListeningHeadPhones(block: (String) -> Unit) {
        this.listeningHeadPhones = block
    }
}