package com.example.clothessuggester.model

data class WeatherRequest(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)