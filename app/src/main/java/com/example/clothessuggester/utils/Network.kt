package com.example.clothessuggester.utils

import android.util.Log
import com.example.clothessuggester.BuildConfig
import com.example.clothessuggester.model.WeatherResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

object Network {
    private const val BASE_URL =  "api.weatherstack.com"
    private val gson = Gson()
    private val client: OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    fun makeRequestUsingOkhttp(locationParam: String, callback: WeatherCallback) {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host(BASE_URL)
            .addPathSegment("current")
            .addQueryParameter("access_key", BuildConfig.API_KEY)
            .addQueryParameter("query", locationParam)
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "Unknown error")
                Log.e("najeia", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    val weatherResponse = gson.fromJson(responseString, WeatherResponse::class.java)
                    callback.onSuccess(weatherResponse)
                    Log.e("najeia", weatherResponse.toString())
                } else {
                    callback.onError(response.message)
                }
            }
        })
    }
}
