package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import uz.mlsoft.mybroadcastpractice.R
import uz.mlsoft.mybroadcastpractice.data.SharedPref
import java.util.Locale

class ScreenReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private var listeningScreen: ((String) -> Unit)? = null
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var mediaPlayer :MediaPlayer

    override fun onReceive(context: Context, intent: Intent) {
        myLog("screen reciever")
        textToSpeech = TextToSpeech(context) {
            textToSpeech.language = Locale.US
        }
        if (mySharedPref.screenStateMode) {
            if (mySharedPref.screenStateMode) {
                when (intent.action) {
                    Intent.ACTION_SCREEN_ON -> {
                        mediaPlayer = MediaPlayer.create(context, R.raw.screen_off)
                        mediaPlayer.start()
                        showToast(context, "screen is turned on")
                        textToSpeech.speak(
                            "screen is turned on", TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                        listeningScreen?.invoke("screen is turned on")
                    }

                    Intent.ACTION_SCREEN_OFF -> {
                        mediaPlayer = MediaPlayer.create(context, R.raw.screen_off)
                        mediaPlayer.start()
                        textToSpeech.speak(
                            "screen is turned off", TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                        showToast(context, "screen is turned off")
                        listeningScreen?.invoke("screen is turned off")
                    }
                }
            }

        }

    }

    fun setScreenListener(block: (String) -> Unit) {
        this.listeningScreen = block
    }
}