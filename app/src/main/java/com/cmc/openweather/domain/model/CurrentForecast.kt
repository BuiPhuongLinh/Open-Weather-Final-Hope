package com.cmc.openweather.domain.model

data class CurrentForecast(
    val coord: Coord? = null,
    val weather: List<Weather>? = mutableListOf(),
    val main: Main? = null,
    val name: String? = ""
)

data class Coord(
    val lon: Float? = 0f,
    val lat: Float? = 0f,
)

data class Weather(
    val id: Int? = 0,
    val main: String? = "",
    val description: String? = "",
    val icon: String? = ""
)

data class Main(
    val temp: Float? = 0f,
    val feelsLike: Float? = 0f,
    val tempMin: Float? = 0f,
    val tempMax: Float? = 0f,
    val humidity: Float? = 0f,
)