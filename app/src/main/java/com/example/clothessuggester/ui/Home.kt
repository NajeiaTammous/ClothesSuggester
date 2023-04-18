package com.example.clothessuggester.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.clothessuggester.R
import com.example.clothessuggester.databinding.ActivityHomeBinding
import com.example.clothessuggester.databinding.ClothesItemLayoutBinding
import com.example.clothessuggester.model.ClothesItem
import com.example.clothessuggester.model.Season
import com.example.clothessuggester.model.WeatherResponse
import com.example.clothessuggester.model.getClothesForTemperature
import com.example.clothessuggester.utils.Network
import com.example.clothessuggester.utils.WeatherCallback
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.Locale

class Home : AppCompatActivity() , WeatherCallback {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Network.makeRequestUsingOkhttp("Palestine", this)

    }

    override fun onSuccess(weatherResponse: WeatherResponse) {
        val locationCity = weatherResponse.location.name
        val locationCountry = weatherResponse.location.country
        val weatherStatus = weatherResponse.current.weather_descriptions[0]
        val weatherTemperature = weatherResponse.current.temperature
        val lastUpdatedTimeAndDate= weatherResponse.location.localtime

        val clothes = getClothesForTemperature(weatherTemperature)
        runOnUiThread {
            with(binding) {
                address.text = getString(R.string.location_address, locationCity, locationCountry)
                updatedAt.text = lastUpdatedTimeAndDate
                temp.text = getString(R.string.temperature, weatherTemperature)
                status.text = weatherStatus

                mainContainer.visibility = View.VISIBLE
                errorText.visibility = View.GONE
                loader.visibility = View.GONE

                clothesLayout.removeAllViews()
                clothes?.let {
                    setClothesItems(it, clothesLayout)
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

        val view = layoutInflater.inflate(R.layout.clothes_item_layout, null)
        val clothesImage = view.findViewById<ImageView>(R.id.ClothesImage)
        val clothesTitle = view.findViewById<TextView>(R.id.ClothesTitle)

        clothesImage.setImageResource(clothesItem.imageResourceId)
        clothesTitle.text = clothesItem.name
        linearLayout.addView(view)
    }

//    companion object {
//        private val summerClothes = listOf(
//            ClothesItem("Shorts", R.drawable.green_shirt, Season.SUMMER),
//            ClothesItem("T-Shirt", R.drawable.summer_outfit, Season.SUMMER),
//            ClothesItem("Sunglasses", R.drawable.summer_red_shirt, Season.SUMMER),
//            ClothesItem("Sunglasses", R.drawable.summer_jaket, Season.SUMMER)
//        )
//
//        private val winterClothes = listOf(
//            ClothesItem("Coat", R.drawable.winter_jaket, Season.WINTER),
//            ClothesItem("Sweater", R.drawable.winter_jacket_green, Season.WINTER),
//            ClothesItem("Sweater", R.drawable.winter_gray_set, Season.WINTER),
//            ClothesItem("Gloves", R.drawable.winter_pink_shirt, Season.WINTER)
//        )
//    }

}