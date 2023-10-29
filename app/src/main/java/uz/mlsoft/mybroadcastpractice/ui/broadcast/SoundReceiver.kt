package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class SoundReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningSound: ((String) -> Unit)? = null
    override fun onReceive(context: Context, intent: Intent) {
        myLog("sound receiver")
        if (mySharedPref.soundMode) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val isRingetoneSilent = audioManager.ringerMode == AudioManager.RINGER_MODE_NORMAL
            if (isRingetoneSilent) {
                myLog("off")
                showToast(context, "Ringetone is turned on")
                listeningSound?.invoke("Ringetone is turned on")
            } else {
                showToast(context, "Ringetone is turned off")
                listeningSound?.invoke("Ringetone is turned off")

            }
        }

    }

    fun setSoundListener(block: (String) -> Unit) {
        this.listeningSound = block
    }
}