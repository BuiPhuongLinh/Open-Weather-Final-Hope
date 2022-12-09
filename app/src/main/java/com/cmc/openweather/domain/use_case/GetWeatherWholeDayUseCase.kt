package com.cmc.openweather.domain.use_case

import com.cmc.openweather.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class GetWeatherWholeDayUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    suspend operator fun invoke(lat: Float, long: Float, isCelsius: Boolean) =
        repository.getWholeDayForecast(lat, long, isCelsius)
}