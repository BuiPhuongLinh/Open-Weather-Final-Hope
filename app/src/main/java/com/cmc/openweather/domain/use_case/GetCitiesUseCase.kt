package com.cmc.openweather.domain.use_case

import com.cmc.openweather.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    suspend operator fun invoke(keyword: String) = repository.getCitiesByKeyword(keyword)
}
