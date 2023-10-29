package uz.mlsoft.mybroadcastpractice.data

import android.content.Context
import uz.mlsoft.mybroadcastpractice.app.App

class SharedPref private constructor() {
    companion object {
        private var instance: SharedPref? = null

        fun init() {
            instance = SharedPref()
        }

        fun getPreferences(): SharedPref = instance!!
    }

    private val pref = App.instance.getSharedPreferences("MyShared", Context.MODE_PRIVATE)


    var blueToothMode
        get() = pref.getBoolean("BLUETOOTH", false)
        set(value) = pref.edit().putBoolean("BLUETOOTH", value).apply()

    var internetMode
        get() = pref.getBoolean("INTERNET", false)
        set(value) = pref.edit().putBoolean("INTERNET", value).apply()

    var soundMode
        get() = pref.getBoolean("SOUND", false)
        set(value) = pref.edit().putBoolean("SOUND", value).apply()

    var screenStateMode
        get() = pref.getBoolean("SCREEN", false)
        set(value) = pref.edit().putBoolean("SCREEN", value).apply()


    var locationMode
        get() = pref.getBoolean("LOCATION", false)
        set(value) = pref.edit().putBoolean("LOCATION", value).apply()


    var chargerMode
        get() = pref.getBoolean("CHARGER", false)
        set(value) = pref.edit().putBoolean("CHARGER", value).apply()

    var airPlaneMode
        get() = pref.getBoolean("AIR", false)
        set(value) = pref.edit().putBoolean("AIR", value).apply()

    var batteryLow
        get() = pref.getBoolean("POWER", false)
        set(value) = pref.edit().putBoolean("POWER", value).apply()

    var headPhones
        get() = pref.getBoolean("HEDPHONE", false)
        set(value) = pref.edit().putBoolean("HEDPHONE", value).apply()

    var screenOrientationLocked
        get() = pref.getBoolean("ORIENTATION", false)
        set(value) = pref.edit().putBoolean("ORIENTATION", value).apply()


}
