package com.example.weather.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.Daily
import com.example.weather.databinding.ListItemBinding

class MainScreenAdapter(private val weather: List<Daily>) : RecyclerView.Adapter<MainScreenAdapter.MainScreenViewHolder>() {

    class MainScreenViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherData: Daily) {
            binding.weatherData = weatherData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainScreenViewHolder(ListItemBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        holder.bind(weather[position])
    }

    override fun getItemCount(): Int {
        return weather.size
    }
}