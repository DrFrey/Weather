package com.example.weather.detailscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.Daily
import java.lang.IllegalArgumentException

class DetailViewModel(private val daily: Daily) : ViewModel() {
    private val _weather = MutableLiveData<Daily>()
    val weather : LiveData<Daily>
        get() = _weather

    init {
        _weather.value = daily
    }
}

class DetailViewModelFactory(private var daily: Daily) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(daily) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }

}