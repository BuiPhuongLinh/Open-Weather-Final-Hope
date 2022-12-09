package com.cmc.openweather.data.remote.model.response

import com.cmc.openweather.domain.model.Hourly
import com.cmc.openweather.domain.model.WholeDayForecast
import com.google.gson.annotations.SerializedName

data class WholeDayForecastResponse(
    @SerializedName("timezone") val timezone: String? = "",
    @SerializedName("lon") val lon: String? = "",
    @SerializedName("lat") val lat: String? = "",
    @SerializedName("hourly") val hourlyDTO: MutableList<HourlyDTO>? = mutableListOf(),
)

data class HourlyDTO(
    @SerializedName("dt") val dt: Long? = 0,
    @SerializedName("temp") val temp: Double? = 0.0,
    @SerializedName("humidity") val humidity: Int? = 0,
)

fun WholeDayForecastResponse.toWholeDayForecastModel(): WholeDayForecast {
    return WholeDayForecast(timezone, lon, lat, hourlyDTO?.map { it.toHourlyModel() })
}

fun HourlyDTO.toHourlyModel(): Hourly {
    return Hourly(dt, temp, humidity)
}