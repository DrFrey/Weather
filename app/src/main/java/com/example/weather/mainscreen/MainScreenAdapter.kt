package com.example.weather.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.Daily
import com.example.weather.data.Weather
import com.example.weather.databinding.ListItemBinding

class MainScreenAdapter : ListAdapter<Daily, MainScreenAdapter.MainScreenViewHolder>(WeatherItemDiffCallback()) {

    private var clickListener: OnItemClickListener? = null

    class MainScreenViewHolder(
        private val binding: ListItemBinding,
        clickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.dailyListLayout.setOnClickListener {
                if (clickListener != null) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(adapterPosition)
                    }
                }
            }
        }

        fun bind(weatherData: Daily) {
            binding.weatherData = weatherData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainScreenViewHolder(ListItemBinding.inflate(inflater, parent, false), clickListener)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class WeatherItemDiffCallback : DiffUtil.ItemCallback<Daily>(){
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }
}