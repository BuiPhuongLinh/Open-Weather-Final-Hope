package com.cmc.openweather.data.remote.model.response

import com.cmc.openweather.domain.model.CityForecast
import com.google.gson.annotations.SerializedName

data class CityForecastResponse(
    @SerializedName("name") val name: String? = "",
    @SerializedName("lat") val lat: Float? = 0.0f,
    @SerializedName("lon") val long: Float? = 0.0f,
    @SerializedName("country") val country: String? = ""
)

fun CityForecastResponse.toCityForecastModel(): CityForecast {
    return CityForecast(name, lat, long, country)
}