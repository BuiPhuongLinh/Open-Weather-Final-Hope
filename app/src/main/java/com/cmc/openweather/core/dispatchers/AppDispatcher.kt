package com.cmc.openweather.core.dispatchers

import kotlinx.coroutines.Dispatchers

class AppDispatcher : Dispatcher {
    override val main = Dispatchers.Main

    override val io = Dispatchers.IO

    override val db = Dispatchers.IO

    override val computation = Dispatchers.Default

}
