package uz.mlsoft.mybroadcastpractice.app

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.media.AudioManager
import android.os.Build
import uz.mlsoft.mybroadcastpractice.data.SharedPref
import uz.mlsoft.mybroadcastpractice.ui.broadcast.BlueToothReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.FlashLightReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.InternetReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.LocationReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.ScreenReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.SoundReceiver
import uz.mlsoft.mybroadcastpractice.ui.service.MyService

class App : Application() {
    private lateinit var chargeReciever: FlashLightReceiver
    private lateinit var locationReceiver: LocationReceiver
    private lateinit var soundReciever: SoundReceiver
    private lateinit var screenReceiver: ScreenReceiver
    private lateinit var internetReceiver: InternetReceiver
    private lateinit var bluetoothReceiver: BlueToothReceiver

    private val filterAudio = IntentFilter().apply {
        addAction(AudioManager.RINGER_MODE_CHANGED_ACTION)
    }
    private val filterScreenOn =
        IntentFilter().apply { addAction(Intent.ACTION_SCREEN_ON) }
    private val filterScreenOff =
        IntentFilter().apply { addAction(Intent.ACTION_SCREEN_OFF) }
    private val filterBluetooth =
        IntentFilter().apply { addAction(BluetoothAdapter.ACTION_STATE_CHANGED) }
    private val filter = IntentFilter(LocationManager.MODE_CHANGED_ACTION)
    private val filterMode =
        IntentFilter().apply { addAction("android.os.action.POWER_SAVE_MODE_CHANGED") }

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedPref.init()
    }


}