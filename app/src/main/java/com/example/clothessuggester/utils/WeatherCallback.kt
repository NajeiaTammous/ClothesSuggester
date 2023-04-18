package com.example.clothessuggester.utils

import com.example.clothessuggester.model.WeatherResponse

interface WeatherCallback {
    fun onSuccess(weatherResponse: WeatherResponse)
    fun onError(message: String)

}