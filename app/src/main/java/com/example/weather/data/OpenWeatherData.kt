package com.example.weather.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenWeatherData(
    @Json(name = "alerts")
    val alerts: List<Alert>?,
    @Json(name = "current")
    val current: Current?,
    @Json(name = "daily")
    val daily: List<Daily>?,
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "timezone")
    val timezone: String?,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int?
)