package com.cmc.openweather.domain.model

data class WholeDayForecast(
    val timeZone: String? = "",
    val lon: String? = "",
    val lat: String? = "",
    val hourly: List<Hourly>? = mutableListOf(),
)

data class Hourly(
    val dt: Long? = 0,
    val temp: Double? = 0.0,
    val humidity: Int? = 0,
)