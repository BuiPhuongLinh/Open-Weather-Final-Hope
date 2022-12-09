package com.cmc.openweather.common

fun Float.convertCelsiusToFahrenheit(): Float {
    return (this * 9.0f / 5.0f + 32)
}