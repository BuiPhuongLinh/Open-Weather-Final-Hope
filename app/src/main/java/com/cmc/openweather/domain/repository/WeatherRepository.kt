package com.cmc.openweather.domain.repository

import com.cmc.openweather.common.Resource
import com.cmc.openweather.domain.model.CityForecast
import com.cmc.openweather.domain.model.CurrentForecast
import com.cmc.openweather.domain.model.WholeDayForecast
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {

    suspend fun getCitiesByKeyword(keyWord: String): Flow<Resource<List<CityForecast>>>

    suspend fun getWholeDayForecast(
        lat: Float,
        long: Float,
        isCelsius: Boolean
    ): Flow<Resource<WholeDayForecast>>

    suspend fun getCurrentForecast(lat: Float, long: Float): Flow<Resource<CurrentForecast>>
}
