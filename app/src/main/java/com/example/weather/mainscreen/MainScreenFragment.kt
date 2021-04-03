package com.example.weather.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather.databinding.FragmentMainscreenBinding

class MainScreenFragment: Fragment() {
    private lateinit var binding: FragmentMainscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainscreenBinding.inflate(inflater)
        return binding.root
    }
}