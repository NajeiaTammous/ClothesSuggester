package com.example.clothessuggester.model

data class Current(
    val temperature: Int,
    val weather_descriptions: List<String>,
    val is_day: String
)