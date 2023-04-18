package com.example.clothessuggester.utils

import com.example.clothessuggester.Interceptor.WeatherInterceptors
import com.example.clothessuggester.model.WeatherResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object Network {
    private const val BASE_URL = "http://api.weatherstack.com"
//    private const val ACCESS_KEY = "0e5b635fda05ce8c11adb08a08303522"
//    private const val QUERY_PARAM = "gaza"
    private val gson = Gson()
    private val client: OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(WeatherInterceptors()).build()

    fun makeRequestUsingOkhttp(query: String, accessKey: String, callback: WeatherCallback) {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host(BASE_URL)
            .addPathSegment("current")
            .addQueryParameter("access_key", accessKey)
            .addQueryParameter("query", query)
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    val weatherResponse = gson.fromJson(responseString, WeatherResponse::class.java)
                    callback.onSuccess(weatherResponse)
                } else {
                    callback.onError(response.message)
                }
            }
        })
    }
}
