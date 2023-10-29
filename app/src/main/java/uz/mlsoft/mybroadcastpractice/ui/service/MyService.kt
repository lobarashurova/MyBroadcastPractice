package uz.mlsoft.mybroadcastpractice.ui.service

import android.app.Notification
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.IBinder
import uz.mlsoft.mybroadcastpractice.data.SharedPref
import uz.mlsoft.mybroadcastpractice.ui.broadcast.AirplaneModeReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.BatteryLowReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.BlueToothReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.FlashLightReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.HeadPhonesReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.InternetReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.LocationReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.ScreenOrientationReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.ScreenReceiver
import uz.mlsoft.mybroadcastpractice.ui.broadcast.SoundReceiver
import uz.mlsoft.mybroadcastpractice.ui.notification.NotificationHelper

class MyService : Service() {
    private val notificationHelper by lazy { NotificationHelper(this) }
    private val notification: Notification by lazy { notificationHelper.getNotification() }
    private val myShared by lazy { SharedPref.getPreferences() }
    private lateinit var chargeReciever: FlashLightReceiver
    private lateinit var locationReceiver: LocationReceiver
    private lateinit var soundReciever: SoundReceiver
    private lateinit var screenReceiver: ScreenReceiver
    private lateinit var internetReceiver: InternetReceiver
    private lateinit var bluetoothReceiver: BlueToothReceiver
    private lateinit var airplaneModeReceiver: AirplaneModeReceiver
    private lateinit var screenOrientationReceiver: ScreenOrientationReceiver
    private lateinit var batteryLowReceiver: BatteryLowReceiver
    private lateinit var headPhonesReceiver: HeadPhonesReceiver

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
    private val orientaitonFIlter = IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED)
    private val airplaneFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
    private val headPhoneFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
    private val batteryFIlter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(12, notification)
        val orientation = resources.configuration.orientation
        chargeReciever = FlashLightReceiver(myShared)
        locationReceiver = LocationReceiver(myShared)
        bluetoothReceiver = BlueToothReceiver(myShared)
        soundReciever = SoundReceiver(myShared)
        internetReceiver = InternetReceiver(myShared)
        bluetoothReceiver = BlueToothReceiver(myShared)
        screenReceiver = ScreenReceiver(myShared)
        batteryLowReceiver = BatteryLowReceiver(myShared)
        headPhonesReceiver = HeadPhonesReceiver(myShared)
        screenOrientationReceiver = ScreenOrientationReceiver(orientation = orientation, myShared)
        airplaneModeReceiver = AirplaneModeReceiver(myShared)



        registerReceiver(screenReceiver, filterScreenOff)
        registerReceiver(screenReceiver, filterScreenOn)
        registerReceiver(soundReciever, filterAudio)
        registerReceiver(internetReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        registerReceiver(bluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        registerReceiver(locationReceiver, filter)
        registerReceiver(chargeReciever, filterMode)
        registerReceiver(airplaneModeReceiver, airplaneFilter)
        registerReceiver(screenOrientationReceiver, orientaitonFIlter)
        registerReceiver(batteryLowReceiver, batteryFIlter)
        registerReceiver(headPhonesReceiver, headPhoneFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

}