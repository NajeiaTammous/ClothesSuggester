package com.example.clothessuggester.local

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SharedPrefsUtils {
    object ClothesPrefsUtil {
        private var sharedPreferences: SharedPreferences? = null
        private const val LAST_SUGGESTED_ITEM_NAME_KEY = "LAST_SUGGESTED_ITEM_NAME_KEY"
        private const val LAST_SUGGESTED_ITEM_DATE_KEY = "LAST_SUGGESTED_ITEM_DATE_KEY"
        private const val LAST_SUGGESTED_ITEM_IMAGE_URI_KEY = "LAST_SUGGESTED_ITEM_IMAGE_URI_KEY"


        fun initPrefs(context: Context) {
            sharedPreferences = context.getSharedPreferences(
                "ClothesPreferences",
                Context.MODE_PRIVATE
            )
        }

        fun saveLastSuggestedItem(itemName: String, date: String, imageUri: Int) {
            sharedPreferences?.edit()?.apply {
                putString(LAST_SUGGESTED_ITEM_NAME_KEY, itemName)
                putString(LAST_SUGGESTED_ITEM_DATE_KEY, date)
                putInt(LAST_SUGGESTED_ITEM_IMAGE_URI_KEY, imageUri)
                apply()
            }
        }

        fun getLastSuggestedItem(localDate: String): Pair<String?, Int?>? {
            val lastSuggestedDate = sharedPreferences?.getString(LAST_SUGGESTED_ITEM_DATE_KEY, null)
            val lastSuggestedItemName = sharedPreferences?.getString(LAST_SUGGESTED_ITEM_NAME_KEY, null)
            val lastSuggestedItemImage = sharedPreferences?.getInt(LAST_SUGGESTED_ITEM_IMAGE_URI_KEY, 0)
            return if (lastSuggestedDate == localDate) {
                Pair(lastSuggestedItemName, lastSuggestedItemImage)
            } else {
                null
            }
        }

    }
}