package com.example.weather.data


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Alert(
    @Json(name = "description")
    val description: String?,
    @Json(name = "end")
    val end: Long?,
    @Json(name = "event")
    val event: String?,
    @Json(name = "sender_name")
    val senderName: String?,
    @Json(name = "start")
    val start: Long?
) : Parcelable