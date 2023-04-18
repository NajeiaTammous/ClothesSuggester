package com.example.clothessuggester.Interceptor

import okhttp3.Interceptor
import okhttp3.Response

class WeatherInterceptors: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mRequest = chain.request().newBuilder()
        return chain.proceed(mRequest.build())
    }
}