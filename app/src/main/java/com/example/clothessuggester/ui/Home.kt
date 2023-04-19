package com.example.clothessuggester.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.clothessuggester.R
import com.example.clothessuggester.databinding.ActivityHomeBinding
import com.example.clothessuggester.databinding.ClothesItemLayoutBinding
import com.example.clothessuggester.local.SharedPrefsUtils
import com.example.clothessuggester.model.ClothesItem
import com.example.clothessuggester.model.Season
import com.example.clothessuggester.model.WeatherResponse
import com.example.clothessuggester.model.getClothesForTemperature
import com.example.clothessuggester.utils.Network
import com.example.clothessuggester.utils.WeatherCallback
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

class Home : AppCompatActivity() , WeatherCallback {
    private lateinit var binding: ActivityHomeBinding
    private var lastSuggestedItem: Pair<String?, Int?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPrefsUtils.ClothesPrefsUtil.initPrefs(this)
        Network.makeRequestUsingOkhttp("Palestine", this)


    }

    override fun onSuccess(weatherResponse: WeatherResponse) {
        val locationCity = weatherResponse.location.name
        val locationCountry = weatherResponse.location.country
        val weatherStatus = weatherResponse.current.weather_descriptions[0]
        val weatherTemperature = weatherResponse.current.temperature
        val lastUpdatedTimeAndDate = weatherResponse.location.localtime
        var clothes = getClothesForTemperature(weatherTemperature)

        runOnUiThread {
            with(binding) {
                address.text = getString(R.string.location_address, locationCity, locationCountry)
                updatedAt.text = lastUpdatedTimeAndDate
                temp.text = getString(R.string.temperature, weatherTemperature)
                status.text = weatherStatus

                mainContainer.visibility = View.VISIBLE
                errorText.visibility = View.GONE
                loader.visibility = View.GONE

                lastSuggestedItem = SharedPrefsUtils.ClothesPrefsUtil
                    .getLastSuggestedItem(lastUpdatedTimeAndDate)
//                clothesLayout.removeAllViews()
                if (lastSuggestedItem == null) {
                    val clothes = getClothesForTemperature(weatherTemperature)
                    val randomClothes = clothes ?: ClothesItem("No clothes available", 0, null)
                    val suggestedItem = Pair(randomClothes.name, randomClothes.imageResourceId)
                    SharedPrefsUtils.ClothesPrefsUtil.saveLastSuggestedItem(suggestedItem.first, lastUpdatedTimeAndDate, suggestedItem.second)
                    setClothesItems(randomClothes, clothesLayout)
                } else {
                    SharedPrefsUtils.ClothesPrefsUtil.saveLastSuggestedItem(lastSuggestedItem!!.first!!, lastUpdatedTimeAndDate, lastSuggestedItem!!.second!!)
                    val clothes = ClothesItem(lastSuggestedItem!!.first!!, lastSuggestedItem!!.second!!, null)
                    setClothesItems(clothes, clothesLayout)
                }
            }
        }
    }

    override fun onError(message: String) {
        runOnUiThread {
            binding.apply {
                errorText.visibility = View.VISIBLE
                mainContainer.visibility = View.GONE
                loader.visibility = View.GONE
            }
        }
    }

    fun setClothesItems(clothesItem: ClothesItem, linearLayout: LinearLayout) {
        lastSuggestedItem?.let {
            val view = layoutInflater.inflate(R.layout.clothes_item_layout, null)
            val clothesImage = view.findViewById<ImageView>(R.id.ClothesImage)
            val clothesTitle = view.findViewById<TextView>(R.id.ClothesTitle)

            clothesImage.setImageResource(clothesItem.imageResourceId)
            Log.e("getit", clothesItem.imageResourceId.toString())
            clothesTitle.text = it.first
            linearLayout.addView(view)

        }
    }

}