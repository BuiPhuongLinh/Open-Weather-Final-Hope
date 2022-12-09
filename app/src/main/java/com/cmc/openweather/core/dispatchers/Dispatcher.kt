package com.cmc.openweather.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {

    /** Dispatch work on Android's Main Thread **/
    val main: CoroutineDispatcher

    /** Dispatch work on a background thread (from a pool) for Networking **/
    val io: CoroutineDispatcher

    /** Dispatch work on a Database thread **/
    val db: CoroutineDispatcher

    /** Dispatch computationally-heavy work on a background thread - defined by number of cores **/
    val computation: CoroutineDispatcher
}