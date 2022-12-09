package com.cmc.openweather.data.repository

import com.cmc.openweather.R
import com.cmc.openweather.common.Resource
import com.cmc.openweather.common.TemperatureUnit
import com.cmc.openweather.common.UiText
import com.cmc.openweather.data.remote.OpenWeatherApi
import com.cmc.openweather.data.remote.model.response.toCityForecastModel
import com.cmc.openweather.data.remote.model.response.toCurrentForecastModel
import com.cmc.openweather.data.remote.model.response.toWholeDayForecastModel
import com.cmc.openweather.domain.model.CityForecast
import com.cmc.openweather.domain.model.CurrentForecast
import com.cmc.openweather.domain.model.WholeDayForecast
import com.cmc.openweather.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val apiService: OpenWeatherApi
) : OpenWeatherRepository {

    override suspend fun getCitiesByKeyword(keyWord: String): Flow<Resource<List<CityForecast>>> {
        return try {
            flow {
                emit(Resource.Loading())
                val list = apiService.getCities(keyWord).map { it.toCityForecastModel() }
                emit(Resource.Success(list))
            }
        } catch (e: HttpException) {
            flow {
                emit(
                    Resource.Error(UiText.StringResource(R.string.error_couldnt_reach_server))
                )
            }
        } catch (e: IOException) {
            flow {
                emit(
                    Resource.Error(UiText.StringResource(R.string.error_something_wrong))
                )
            }
        }
    }

    override suspend fun getWholeDayForecast(
        lat: Float,
        long: Float,
        isCelsius: Boolean
    ): Flow<Resource<WholeDayForecast>> {
        return try {
            flow {
                emit(Resource.Loading())

                val wholeDayForecast =
                    apiService.getWholeDayForecast(
                        lat,
                        long,
                        if (isCelsius) TemperatureUnit.CELSIUS.apiValue else TemperatureUnit.FAHRENHEIT.apiValue
                    ).toWholeDayForecastModel()
                emit(Resource.Success(wholeDayForecast))
            }
        } catch (e: HttpException) {
            flow {
                emit(
                    Resource.Error(UiText.StringResource(R.string.error_couldnt_reach_server))
                )
            }
        } catch (e: IOException) {
            flow {
                emit(
                    Resource.Error(UiText.StringResource(R.string.error_something_wrong))
                )
            }
        }
    }

    override suspend fun getCurrentForecast(
        lat: Float,
        long: Float
    ): Flow<Resource<CurrentForecast>> {
        return try {
            flow {
//                emit(Resource.Loading())
                // TODO handle later
                val currentForecast =
                    apiService.getCurrentForecast(lat, long).toCurrentForecastModel()
                emit(Resource.Success(currentForecast))
            }
        } catch (e: HttpException) {
            flow {
                emit(
                    Resource.Error(UiText.StringResource(R.string.error_couldnt_reach_server))
                )
            }
        } catch (e: IOException) {
            flow {
                emit(
                    Resource.Error(UiText.StringResource(R.string.error_something_wrong))
                )
            }
        }
    }
}
