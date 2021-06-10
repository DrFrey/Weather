package com.example.weather.detailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weather.databinding.FragmentDetailsBinding

class DetailFragment: Fragment() {
    private lateinit var binding : FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)

        val args : DetailFragmentArgs by navArgs()
        val weather = args.dailyWeather

        val viewModel : DetailViewModel by viewModels {
            DetailViewModelFactory(weather)
        }

        viewModel.weather.observe(this, {
            binding.dailyWeather = it
        })

        return binding.root
    }
}