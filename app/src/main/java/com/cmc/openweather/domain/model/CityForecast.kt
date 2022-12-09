package com.cmc.openweather.domain.model

data class CityForecast(
    val name: String? = "",
    val lat: Float? = 0.0f,
    val long: Float? = 0.0f,
    val country: String? = ""
)
