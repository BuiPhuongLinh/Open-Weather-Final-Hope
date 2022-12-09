package com.cmc.openweather.presentation.wholeday

import com.cmc.openweather.common.Resource
import com.cmc.openweather.core.dispatchers.Dispatcher
import com.cmc.openweather.core.viewmodel.BaseViewModel
import com.cmc.openweather.domain.model.WholeDayForecast
import com.cmc.openweather.domain.use_case.GetWeatherWholeDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WholeDayViewModel @Inject constructor(
    private val useCase: GetWeatherWholeDayUseCase,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    private val _wholeDayForecast =
        MutableStateFlow<Resource<WholeDayForecast>>(Resource.Loading())
    val wholeDayForecast = _wholeDayForecast.asStateFlow()

    var lat: Float = 0f
    var long: Float = 0f
    var isCelsius: Boolean = true

    fun getWholeDayForecast(lat: Float?, long: Float?, isCelsius: Boolean) {
        this.lat = lat ?: 0f
        this.long = long ?: 0f
        this.isCelsius = isCelsius
        launchOnMain {
            useCase.invoke(this@WholeDayViewModel.lat, this@WholeDayViewModel.long, isCelsius).collect {
                _wholeDayForecast.value = it
            }
        }
    }
}
