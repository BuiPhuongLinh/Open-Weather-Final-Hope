package com.cmc.openweather.presentation.detail

import com.cmc.openweather.common.Resource
import com.cmc.openweather.common.convertCelsiusToFahrenheit
import com.cmc.openweather.core.dispatchers.Dispatcher
import com.cmc.openweather.core.viewmodel.BaseViewModel
import com.cmc.openweather.domain.model.CurrentForecast
import com.cmc.openweather.domain.use_case.GetCurrentForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val getCurrentForecastUseCase: GetCurrentForecastUseCase,
    dispatcher: Dispatcher,
) : BaseViewModel(dispatcher) {

    private var _currentForecast = MutableStateFlow<Resource<CurrentForecast>>(Resource.Loading())
    var isCelsiusUnit: Boolean = true
    val currentForecast = _currentForecast
    var lat: Float = 0f
    var long: Float = 0f

    fun getCurrentForecast(lat: Float, long: Float) {
        this.lat = lat
        this.long = long
        launchOnMain {
            getCurrentForecastUseCase.invoke(lat, long).collect {
                _currentForecast.value = it
            }
        }
    }

    fun changeTemperatureUnit(): Float {
        isCelsiusUnit = !isCelsiusUnit
        return if (isCelsiusUnit) {
            _currentForecast.value.data?.main?.temp ?: 0f
        } else {
            (_currentForecast.value.data?.main?.temp ?: 0f).convertCelsiusToFahrenheit()
        }
    }
}
