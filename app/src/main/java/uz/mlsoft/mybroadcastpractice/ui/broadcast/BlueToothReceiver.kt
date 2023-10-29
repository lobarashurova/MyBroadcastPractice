package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class BlueToothReceiver constructor(private val mySharedPref: SharedPref) : BroadcastReceiver() {
    private lateinit var textToSpeech: TextToSpeech
    private var listeningBlueTooth: ((String) -> Unit)? = null

    override fun onReceive(context: Context, intent: Intent) {
        myLog("onReceive bluetooth")
        textToSpeech = TextToSpeech(context) { status -> }
        myLog("bluetooth state rec: ${mySharedPref.blueToothMode}")
        myLog("shared pref object : ${mySharedPref.toString()}")

        if (mySharedPref.blueToothMode) {
            if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {

                    BluetoothAdapter.STATE_OFF -> {
                        listeningBlueTooth?.invoke("bluetooth state is off")
                        textToSpeech.speak(
                            "bluetooth state is off",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }

                    BluetoothAdapter.STATE_ON -> {
                        listeningBlueTooth?.invoke("bluetooth state is off")
                        textToSpeech.speak(
                            "bluetooth state is on",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }

                    BluetoothAdapter.STATE_CONNECTED -> {
                        listeningBlueTooth?.invoke("bluetooth state is connected")
                        textToSpeech.speak(
                            "bluetooth state is connected",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }

                    BluetoothAdapter.STATE_DISCONNECTED -> {
                        listeningBlueTooth?.invoke("bluetooth state is disconnected")
                        textToSpeech.speak(
                            "bluetooth state is disconnected",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }

    fun setListeningBlueTooth(block: (String) -> Unit) {
        this.listeningBlueTooth = block
    }
}