package com.example.weather

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.round

@BindingAdapter("formattedDateTime")
fun setFormattedDateTime(textView: TextView, dateLong: Long?) {

    if (dateLong == null) {
        textView.text = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM"))
    } else {
        try {
            val ld = Instant.ofEpochSecond(dateLong).atZone(ZoneId.systemDefault()).toLocalDate()
            val formattedDate = ld.format(DateTimeFormatter.ofPattern("d MMMM"))
            Log.d("___W", "formatted date: $formattedDate")
            textView.text = formattedDate
        } catch (e: Exception) {
            textView.text = dateLong.toString()
            Log.d("___W", "error formatting date: ${e.message}")
        }
    }
}

@BindingAdapter("convertTemp")
fun setDegrees(textView: TextView, temp: Double) {
    val res = round(temp).toInt().toString()
    textView.text = "${res}\u00B0"
}

@BindingAdapter("loadWeatherIcon")
fun loadImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val finalUrl = "https://openweathermap.org/img/wn/" + url + "@2x.png"
        Glide.with(imageView.context)
            .load(finalUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic__21_sun)
                    .error(R.drawable.ic_round_signal_wifi_off_24)
            )
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.ic__21_sun)
    }
}