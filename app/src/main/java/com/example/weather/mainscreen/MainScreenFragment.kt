package com.example.weather.mainscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weather.databinding.FragmentMainscreenBinding

class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainscreenBinding
    private lateinit var viewModel: MainScreenViewModel

    companion object {
        const val REQUEST_FOR_LOCATION_PERMISSION = 44
        const val LOGTAG = "LOGTAG"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainscreenBinding.inflate(inflater)
        viewModel = MainScreenViewModel(requireActivity())

        if (checkPermission()) {
            viewModel.locationPermissionGranted = true
            Log.d("___W", "permission exists")
            if (viewModel.isLocationEnabled()) {
                Log.d("___W", "location is enabled")
                viewModel.getLocation()
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            Log.d("___W", "no permission")
            requestPermission()
        }



        viewModel.locationReceived.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    "Location: ${viewModel.lat} and ${viewModel.lon}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return binding.root
    }

    private fun checkPermission() : Boolean {
        Log.d("___W", "checking permission")
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        Log.d("___W", "requesting permission")
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_FOR_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("___W", "request permission callback")
        if (requestCode == REQUEST_FOR_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("___W", "permission granted")
                viewModel.locationPermissionGranted = true
                viewModel.getLocation()
            } else {
                Log.d("___W", "permission denied")
                viewModel.locationPermissionGranted = false
            }
        }
    }
}


