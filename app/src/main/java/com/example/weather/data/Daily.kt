package com.example.weather.data


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Daily(
    @Json(name = "clouds")
    val clouds: Int?,
    @Json(name = "dew_point")
    val dewPoint: Double?,
    @Json(name = "dt")
    val dt: Long?,
    @Json(name = "feels_like")
    val feelsLike: FeelsLike?,
    @Json(name = "humidity")
    val humidity: Int?,
    @Json(name = "moon_phase")
    val moonPhase: Double?,
    @Json(name = "moonrise")
    val moonrise: Long?,
    @Json(name = "moonset")
    val moonset: Long?,
    @Json(name = "pop")
    val pop: Double?,
    @Json(name = "pressure")
    val pressure: Int?,
    @Json(name = "rain")
    val rain: Double?,
    @Json(name = "sunrise")
    val sunrise: Long?,
    @Json(name = "sunset")
    val sunset: Long?,
    @Json(name = "temp")
    val temp: Temp?,
    @Json(name = "uvi")
    val uvi: Double?,
    @Json(name = "weather")
    val weather: List<WeatherX>?,
    @Json(name = "wind_deg")
    val windDeg: Int?,
    @Json(name = "wind_gust")
    val windGust: Double?,
    @Json(name = "wind_speed")
    val windSpeed: Double?
) : Parcelable