package com.cmc.openweather.domain.use_case

import com.cmc.openweather.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class GetCurrentForecastUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    suspend operator fun invoke(lat: Float, long: Float) = repository.getCurrentForecast(lat, long)
}
