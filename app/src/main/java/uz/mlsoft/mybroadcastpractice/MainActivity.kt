package uz.mlsoft.mybroadcastpractice

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import uz.mlsoft.mybroadcastpractice.ui.broadcast.myLog
import uz.mlsoft.mybroadcastpractice.ui.components.BroadcastItem
import uz.mlsoft.mybroadcastpractice.ui.service.MyService
import uz.mlsoft.mybroadcastpractice.ui.theme.MyBroadcastPracticeTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    private lateinit var textToSpeech: TextToSpeech
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
    private lateinit var mediaPlayer:MediaPlayer

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentService = Intent(this, MyService::class.java)
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(intentService)
        } else startService(intentService)
        textToSpeech = TextToSpeech(this) {
            textToSpeech.language = Locale.US
        }
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


//        registerReceiver(screenReceiver, filterScreenOff)
//        registerReceiver(screenReceiver, filterScreenOn)
//        registerReceiver(soundReciever, filterAudio)
//        registerReceiver(internetReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
//        registerReceiver(bluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
//        registerReceiver(locationReceiver, filter)
//        registerReceiver(chargeReciever, filterMode)
//        registerReceiver(airplaneModeReceiver, airplaneFilter)
//        registerReceiver(screenOrientationReceiver, orientaitonFIlter)
//        registerReceiver(batteryLowReceiver, batteryFIlter)
//        registerReceiver(headPhonesReceiver, headPhoneFilter)


        screenOrientationReceiver.setScreenListener {
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        batteryLowReceiver.setBattery {
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        headPhonesReceiver.setListeningHeadPhones {
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }

        airplaneModeReceiver.setAirplaneModeReceiver {
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        soundReciever.setSoundListener {
            myLog("sound callback")
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        locationReceiver.setListeningLocation {
            myLog("location callback")
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        chargeReciever.setListeningCharger {
            myLog("charger callback")
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        screenReceiver.setScreenListener {
            myLog("screen callback")
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        internetReceiver.setListeningInternet {
            myLog("internet callback")
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }

        bluetoothReceiver.setListeningBlueTooth {
            myLog("bluetooth callback")
            textToSpeech.speak(
                it, TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
        setContent {
            MyBroadcastPracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isCheckedBluetooth by remember { mutableStateOf(false) }
                    var isCheckedRingtone by remember { mutableStateOf(false) }
                    var isCheckedWifi by remember { mutableStateOf(false) }
                    var isCheckedLocationMode by remember { mutableStateOf(false) }
                    var isCheckedScreenRotation by remember { mutableStateOf(false) }
                    var isCheckedFlashligthMode by remember { mutableStateOf(false) }
                    var isCheckedAirplaneMode by remember { mutableStateOf(false) }
                    var isCheckedScreenOrientationMode by remember { mutableStateOf(false) }
                    var isCheckedBatteryLow by remember { mutableStateOf(false) }
                    var isCheckedHeadPhones by remember { mutableStateOf(false) }



                    isCheckedLocationMode = myShared.locationMode
                    isCheckedBluetooth = myShared.blueToothMode
                    isCheckedFlashligthMode = myShared.chargerMode
                    isCheckedRingtone = myShared.soundMode
                    isCheckedWifi = myShared.internetMode
                    isCheckedScreenRotation = myShared.screenStateMode
                    isCheckedAirplaneMode = myShared.airPlaneMode
                    isCheckedScreenOrientationMode = myShared.screenOrientationLocked
                    isCheckedHeadPhones = myShared.headPhones
                    isCheckedBatteryLow = myShared.batteryLow

                    myLog("bluetooth:$isCheckedBluetooth ")
                    myLog("bluetooth pref: ${myShared.blueToothMode}")
                    myLog("shared pref object : ${myShared.toString()}")
                    val intent = Intent(this, MyService::class.java)
                    startService(intent)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "Your event listeners:",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(top = 25.dp)

                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Bluetooth",
                            isChecked = isCheckedBluetooth,
                            image = R.drawable.bluetooth,
                            checkingReceiver = {
                                isCheckedBluetooth = it
                                myShared.blueToothMode = it
                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Screen is on or off",
                            isChecked = isCheckedScreenRotation,
                            image = R.drawable.smartphone,
                            checkingReceiver = {
                                isCheckedScreenRotation = it
                                myShared.screenStateMode = it

                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Location mode",
                            isChecked = isCheckedLocationMode,
                            image = R.drawable.location,
                            checkingReceiver = {
                                isCheckedLocationMode = it
                                myShared.locationMode = it

                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Charging mode",
                            isChecked = isCheckedFlashligthMode,
                            image = R.drawable.charging,
                            checkingReceiver = {
                                isCheckedFlashligthMode = it
                                myShared.chargerMode = it
                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Sound mode",
                            isChecked = isCheckedRingtone,
                            image = R.drawable.bell,
                            checkingReceiver = {
                                isCheckedRingtone = it
                                myShared.soundMode = it
                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Wi fi mode",
                            isChecked = isCheckedWifi,
                            image = R.drawable.wifi,
                            checkingReceiver = {
                                isCheckedWifi = it
                                myShared.internetMode = it
                            })

                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Airplane mode",
                            isChecked = isCheckedAirplaneMode,
                            image = R.drawable.plane,
                            checkingReceiver = {
                                isCheckedAirplaneMode = it
                                myShared.airPlaneMode = it
                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Battery Low mode",
                            isChecked = isCheckedBatteryLow,
                            image = R.drawable.battery,
                            checkingReceiver = {
                                isCheckedBatteryLow = it
                                myShared.batteryLow = it
                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Screen orientation locked",
                            isChecked = isCheckedScreenOrientationMode,
                            image = R.drawable.lock,
                            checkingReceiver = {
                                isCheckedScreenOrientationMode = it
                                myShared.screenOrientationLocked = it
                            })
                        Spacer(modifier = Modifier.size(10.dp))
                        BroadcastItem(
                            name = "Head phones mode",
                            isChecked = isCheckedHeadPhones,
                            image = R.drawable.headphones,
                            checkingReceiver = {
                                isCheckedHeadPhones = it
                                myShared.headPhones = it
                            })
                    }
                }
            }
        }
    }
}
