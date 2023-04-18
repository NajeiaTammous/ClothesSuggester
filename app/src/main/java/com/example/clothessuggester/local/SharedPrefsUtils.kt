package com.example.clothessuggester.local

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsUtils {
    object WeatherPrefsUtil {
        private var sharedPreferences: SharedPreferences? = null
        private const val LOCATION_ADDRESS_KEY = "ADDRESS_KEY"
        private const val LAST_UPDATED_AT_KEY = "UPDATED_AT_KEY"
        private const val STATUS_KEY = "STATUS_KEY"
        private const val TEMPERATURE_KEY = "TEMP_KEY"

        fun initPref(context: Context) {
            sharedPreferences = context.getSharedPreferences(
                "WeatherPreferences",
                Context.MODE_PRIVATE
            )
        }

        var locationAddress: String?
            get() = sharedPreferences?.getString(LOCATION_ADDRESS_KEY, null)
            set(value) {
                value?.let { sharedPreferences?.edit()?.putString(LOCATION_ADDRESS_KEY, it) }?.apply()
            }

        var lastUpdatedTime: String?
            get() = sharedPreferences?.getString(LAST_UPDATED_AT_KEY, null)
            set(value) {
                value?.let { sharedPreferences?.edit()?.putString(LAST_UPDATED_AT_KEY, it) }?.apply()
            }

        var status: String?
            get() = sharedPreferences?.getString(STATUS_KEY, null)
            set(value) {
                value?.let { sharedPreferences?.edit()?.putString(STATUS_KEY, it) }?.apply()
            }

        var temperature: String?
            get() = sharedPreferences?.getString(TEMPERATURE_KEY, null)
            set(value) {
                value?.let { sharedPreferences?.edit()?.putString(TEMPERATURE_KEY, it) }?.apply()
            }
    }


}