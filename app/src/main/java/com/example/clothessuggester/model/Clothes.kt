package com.example.clothessuggester.model

import com.example.clothessuggester.R

data class ClothesItem(
    val name: String,
    val imageResourceId: Int,
    val season: Season?
)

enum class Season(
    val temperatureRange: IntRange,
    val clothes: List<ClothesItem>
) {
    SUMMER(16..50, listOf(
        ClothesItem("Your Summer Green TopShirt", R.drawable.green_shirt, Season.SUMMER),
        ClothesItem("Your Beautiful Summer Suit", R.drawable.summer_outfit, Season.SUMMER),
        ClothesItem("your Red T-shirt", R.drawable.summer_red_shirt, Season.SUMMER),
        ClothesItem("your Summer Colorful Jacket", R.drawable.summer_jaket, Season.SUMMER)
    )),
    WINTER(0..15, listOf(
        ClothesItem("Your Warm Beige Jacket", R.drawable.winter_jaket, Season.WINTER),
        ClothesItem("Your Pastel Green Jacket", R.drawable.winter_jacket_green, Season.WINTER),
        ClothesItem("Your gray pyjamas", R.drawable.winter_gray_set, Season.WINTER),
        ClothesItem("Your Pink Shirt", R.drawable.winter_pink_shirt, Season.WINTER)
    ))
}

fun getClothesForTemperature(temperature: Int): ClothesItem? {
    val season = Season.values().find { temperature in it.temperatureRange }
    val clothes = season?.clothes ?: emptyList()
    return clothes.randomOrNull()
}
