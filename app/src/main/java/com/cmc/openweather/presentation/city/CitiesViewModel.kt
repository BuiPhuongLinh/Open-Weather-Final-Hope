package com.cmc.openweather.presentation.city

import com.cmc.openweather.common.Resource
import com.cmc.openweather.core.dispatchers.Dispatcher
import com.cmc.openweather.core.viewmodel.BaseViewModel
import com.cmc.openweather.domain.model.CityForecast
import com.cmc.openweather.domain.use_case.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    private val _cities = MutableStateFlow<Resource<List<CityForecast>>>(Resource.Loading())
    val cities = _cities.asStateFlow()

    fun getCitiesByKeyWork(keyWord: String) {
        launchOnMain {
            getCitiesUseCase.invoke(keyWord).collect {
                _cities.value = it
            }
        }
    }
}
