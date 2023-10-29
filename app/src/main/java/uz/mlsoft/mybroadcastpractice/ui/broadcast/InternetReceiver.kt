package uz.mlsoft.mybroadcastpractice.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.speech.tts.TextToSpeech
import uz.mlsoft.mybroadcastpractice.data.SharedPref

class InternetReceiver constructor(private val myPref: SharedPref) : BroadcastReceiver() {
    private lateinit var textToSpeech: TextToSpeech
    private var listeningInternet: ((String) -> Unit)? = null

    override fun onReceive(context: Context, intent: Intent) {
        myLog("onReceiver INTERNET ")
        textToSpeech = TextToSpeech(context) {}
        if (myPref.internetMode) {
            if (intent.action == "android.net.conn.CONNECTIVITY_CHANGE") {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = cm.activeNetworkInfo
                val bool = info != null && info.isConnected && info.isAvailable
                if (bool) {
                    listeningInternet?.invoke("Internet is  available")
                    textToSpeech.speak(
                        "iInternet is  available",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )

                } else {
                    listeningInternet?.invoke("Internet is not available")
                    textToSpeech.speak(
                        "Internet is not available",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }
            }
        }
    }

    fun setListeningInternet(block: (String) -> Unit) {
        this.listeningInternet = block
    }
}