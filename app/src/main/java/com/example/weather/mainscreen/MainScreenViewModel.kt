package com.example.weather.mainscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.WeatherApi
import com.example.weather.data.OpenWeatherData
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class MainScreenViewModel(activity: Activity) : ViewModel() {
    var currentDate: LocalDateTime
    var lat: Double = 0.0
    var lon: Double = 0.0
    var locationPermissionGranted: Boolean = false

    var locationManager: LocationManager
    var fusedLocationClient: FusedLocationProviderClient


    private val _locationReceived = MutableLiveData<Boolean>()
    val locationReceived: LiveData<Boolean>
        get() = _locationReceived

    private val _weather = MutableLiveData<OpenWeatherData>()
    val weather: LiveData<OpenWeatherData>
        get() = _weather

    init {
        currentDate = LocalDateTime.now()
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }


    fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        if (locationPermissionGranted) {
            Log.d("___W", "getting location")
            fusedLocationClient.lastLocation.addOnCompleteListener() { task ->
                val location: Location? = task.result
                if (location == null) {
                    getNewLocationData()
                } else {
                    lat = location.latitude
                    lon = location.longitude
                    _locationReceived.value = true
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getNewLocationData() {
        Log.d("___W", "getting new data")
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d("___W", "new location callback")
            val location = locationResult.lastLocation
            lat = location.latitude
            lon = location.longitude
            _locationReceived.value = true
        }
    }

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _weather.value = WeatherApi.retrofitService.getWeather(lat, lon)
                Log.d("___W", "response: ${weather.value}")
            } catch (e: Exception) {
                Log.d("___W", "Exception: ${e.message}")
            }
        }
    }
}