package com.example.weather

import com.example.weather.data.OpenWeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val EXCLUDE = "minutely,hourly"
private const val UNITS = "metric"
private const val LANG = "en"
private const val ACCESS_KEY = "446b8213f853e5398b0d391b3d5710bc"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = EXCLUDE,
        @Query("appid") appid: String = ACCESS_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") lang: String = LANG
    ) : OpenWeatherData
}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}