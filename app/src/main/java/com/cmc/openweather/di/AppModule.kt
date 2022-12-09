package com.cmc.openweather.di

import com.cmc.openweather.BuildConfig
import com.cmc.openweather.core.dispatchers.AppDispatcher
import com.cmc.openweather.core.dispatchers.Dispatcher
import com.cmc.openweather.data.remote.OpenWeatherApi
import com.cmc.openweather.data.repository.OpenWeatherRepositoryImpl
import com.cmc.openweather.domain.repository.OpenWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl: HttpUrl = original.url

                    val url = originalHttpUrl.newBuilder().build()
                    val requestBuilder: Request.Builder = original.newBuilder().url(url)
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
        }.build()
    }


    @Provides
    @Singleton
    fun provideWeatherApi(okHttpClient: OkHttpClient): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/") // TODO handle later
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenWeatherRepository(api: OpenWeatherApi): OpenWeatherRepository {
        return OpenWeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAppDispatcher(): Dispatcher {
        return AppDispatcher()
    }
}
