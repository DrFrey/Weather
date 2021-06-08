package com.example.weather.data


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FeelsLike(
    @Json(name = "day")
    val day: Double?,
    @Json(name = "eve")
    val eve: Double?,
    @Json(name = "morn")
    val morn: Double?,
    @Json(name = "night")
    val night: Double?
) : Parcelable