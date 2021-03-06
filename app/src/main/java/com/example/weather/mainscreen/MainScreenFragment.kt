package com.example.weather.mainscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weather.databinding.FragmentMainscreenBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainScreenFragment : Fragment(), MainScreenAdapter.OnItemClickListener, OnMapReadyCallback {
    private lateinit var binding: FragmentMainscreenBinding
    private lateinit var viewModel: MainScreenViewModel
    private lateinit var adapter: MainScreenAdapter
    private lateinit var map: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var mySwipeRefreshLayout: SwipeRefreshLayout

    companion object {
        const val REQUEST_FOR_LOCATION_PERMISSION = 44
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainscreenBinding.inflate(inflater)
        viewModel = MainScreenViewModel(requireActivity())
        adapter = MainScreenAdapter()

        val layoutManager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(decoration)
        binding.recyclerView.adapter = adapter

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        mySwipeRefreshLayout = binding.swipeToRefreshLayout

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
                Log.d("___W", "Location: ${viewModel.lat} and ${viewModel.lon}")
                val userLocation = LatLng(viewModel.lat, viewModel.lon)
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        userLocation,
                        12f
                    )
                )
                map.addMarker(
                    MarkerOptions()
                        .position(userLocation)
                )
                mySwipeRefreshLayout.isRefreshing = true
                viewModel.getWeather(viewModel.lat, viewModel.lon)
            }
        })

        viewModel.weather.observe(viewLifecycleOwner, {
            binding.weatherData = it
            val daily = it.daily
            if (daily != null) {
                val list = daily.subList(1, daily.size-1)
                adapter.submitList(list)
            }
            val alerts = it.alerts?.filter { alert -> alert.description?.isNotEmpty() == true }
            if (alerts != null) {
                binding.alertIcon.visibility = View.VISIBLE
                binding.alertIcon.setOnClickListener {
                    val dialog = AlertDisplayDialog.newInstance(ArrayList(alerts))
                    dialog.show(requireActivity().supportFragmentManager, null)
                }
            } else {
                binding.alertIcon.visibility = View.GONE
            }
            mySwipeRefreshLayout.isRefreshing = false
        })

        binding.todayLL.setOnClickListener {
            val todayWeather = viewModel.weather.value?.daily?.get(0)
            Log.d("___W", "today weather clicked: $todayWeather")
            this.findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToDetailFragment(todayWeather!!))
        }
        adapter.setOnItemClickListener(this)

        setHasOptionsMenu(true)

        mySwipeRefreshLayout.setOnRefreshListener {
            Log.d("___W", "refresh menu selected")
            viewModel.getWeather(viewModel.lat, viewModel.lon)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.example.weather.R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.weather.R.id.menu_refresh -> {
                Log.d("___W", "refresh menu selected")
                mySwipeRefreshLayout.isRefreshing = true
                viewModel.getWeather(viewModel.lat, viewModel.lon)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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

    override fun onItemClick(position: Int) {
        Log.d("___W", "position clicked: $position")
        val dailyWeather = viewModel.weather.value?.daily?.get(position+1)
        Log.d("___W", "weather clicked: $dailyWeather")
        this.findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToDetailFragment(dailyWeather!!))
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        Log.d("___W", "map is ready")
    }

    //????????????, ?????????????????????? ?????? MapView
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
