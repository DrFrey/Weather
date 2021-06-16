package com.example.weather.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.Alert
import com.example.weather.databinding.AlertItemBinding

class AlertAdapter : ListAdapter<Alert, AlertAdapter.ViewHolder>(AlertComparator()) {
    class ViewHolder(private var binding: AlertItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alert: Alert){
            binding.alert = alert
            binding.executePendingBindings()
        }
    }

    class AlertComparator : DiffUtil.ItemCallback<Alert>() {
        override fun areItemsTheSame(oldItem: Alert, newItem: Alert): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Alert, newItem: Alert): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlertItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}