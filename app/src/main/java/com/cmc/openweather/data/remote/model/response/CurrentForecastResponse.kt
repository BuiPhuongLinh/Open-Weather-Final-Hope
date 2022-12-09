package com.cmc.openweather.data.remote.model.response

import com.cmc.openweather.domain.model.Coord
import com.cmc.openweather.domain.model.CurrentForecast
import com.cmc.openweather.domain.model.Main
import com.cmc.openweather.domain.model.Weather
import com.google.gson.annotations.SerializedName

data class CurrentForecastResponse(
    @SerializedName("coord") val coord: CoordDTO? = CoordDTO(),
    @SerializedName("weather") val weather: List<WeatherDTO>? = mutableListOf(),
    @SerializedName("main") val main: MainDTO? = MainDTO(),
    @SerializedName("name") val name: String? = ""
)

data class CoordDTO(
    @SerializedName("lon") val lon: Float? = 0f,
    @SerializedName("lat") val lat: Float? = 0f,
)

data class WeatherDTO(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("main") val main: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("icon") val icon: String? = ""
)

data class MainDTO(
    @SerializedName("temp") val temp: Float? = 0.0F,
    @SerializedName("feels_like") val feels_like: Float? = 0.0F,
    @SerializedName("temp_min") val temp_min: Float? = 0.0F,
    @SerializedName("temp_max") val temp_max: Float? = 0.0F,
    @SerializedName("humidity") val humidity: Float? = 0.0F
)

fun CurrentForecastResponse.toCurrentForecastModel(): CurrentForecast {
    return CurrentForecast(
        coord?.toCoordModel(),
        weather?.map { it.toWeatherModel() },
        main?.toMainModel(),
        name
    )
}

fun CoordDTO.toCoordModel(): Coord {
    return Coord(lon, lat)
}

fun WeatherDTO.toWeatherModel(): Weather {
    return Weather(id, main, description, icon)
}

fun MainDTO.toMainModel(): Main {
    return Main(temp, feels_like, temp_min, temp_max, humidity)
}