package com.cmc.openweather.presentation.detail

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cmc.openweather.R
import com.cmc.openweather.common.Resource
import com.cmc.openweather.databinding.FragmentCurrentBinding
import com.cmc.openweather.domain.model.CurrentForecast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private lateinit var binding: FragmentCurrentBinding

    private val viewModel: WeatherDetailViewModel by viewModels()
    private var nManager: LocationManager? = null

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                checkLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                checkLocation()
            }
            else -> {
//                 TODO handle later
//                val dialogError = Dialog(requireContext())
//                dialogError.showDialog(getString(R.string.need_allow_permission))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (isLocationPermission()) {
            checkLocation()
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initListeners()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentForecast.collect {
                    when (it) {
                        is Resource.Success -> {
                            bindData(it.data)
                        }
                        is Resource.Error -> {
                            Log.d("xxx", "call error ${it.uiText}")
                        }
                        is Resource.Loading -> {
                            Log.d("xxx", "call loading ${it.uiText}")
                        }
                    }
                }
            }
        }
    }

    private fun bindData(currentForecast: CurrentForecast?) {
        binding.run {
            tvCityName.text = currentForecast?.name ?: ""
            tvTemperature.text = currentForecast?.main?.temp.toString()
            tvHumidity.text =
                getString(R.string.humidity, currentForecast?.main?.humidity.toString())
        }
    }

    private fun initListeners() {
        binding.run {
            btnChangeUnit.setOnClickListener {
                tvTemperature.text = viewModel.changeTemperatureUnit().toString()
                tvTemperatureUnit.text = if (viewModel.isCelsiusUnit) "°C" else "°F"
            }
            edtChangeCity.setOnClickListener {
                findNavController().navigate(R.id.action_currentForecastFragment_to_citiesFragment)
            }
            edtSeeWholeDay.setOnClickListener {
                findNavController().navigate(
                    R.id.action_currentForecastFragment_to_wholedayFragment,
                    bundleOf(
                        "lat" to viewModel.lat,
                        "long" to viewModel.long,
                        "is_celsius" to viewModel.isCelsiusUnit,
                        "city_name" to viewModel.currentForecast.value.data?.name
                    )
                )
            }
        }
    }

    private fun checkLocation() {
        nManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (!nManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!) {
            onGPS()
        } else {
            getLocation()
        }
    }


    private fun onGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.enable_gps))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            val locationGPS = nManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = arguments?.getFloat("lat", locationGPS.latitude.toFloat())
                    ?: locationGPS.latitude.toFloat()
                val long = arguments?.getFloat("long", locationGPS.longitude.toFloat())
                    ?: locationGPS.longitude.toFloat()

                viewModel.getCurrentForecast(lat, long)
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage("Please grant location permission in your setting!")
                    .setOnCancelListener {
                        exitProcess(-1)
                    }.show()
            }
        }
    }

    private fun isLocationPermission(): Boolean {
        return (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && requireContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }
}
