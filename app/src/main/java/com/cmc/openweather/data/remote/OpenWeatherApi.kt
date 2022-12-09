package com.cmc.openweather.data.remote

import com.cmc.openweather.BuildConfig
import com.cmc.openweather.data.remote.model.response.CityForecastResponse
import com.cmc.openweather.data.remote.model.response.CurrentForecastResponse
import com.cmc.openweather.data.remote.model.response.WholeDayForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("geo/1.0/direct")
    suspend fun getCities(
        @Query("q") keyWord: String = "",
        @Query("limit") limit: Int = 10,
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): List<CityForecastResponse>

    @GET("data/2.5/weather/")
    suspend fun getCurrentForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): CurrentForecastResponse

    @GET("data/2.5/onecall")
    suspend fun getWholeDayForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") units: String = "metric",
        @Query("exclude", encoded = true) exclude: String = "current,daily,alerts,minutely",
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): WholeDayForecastResponse
}
