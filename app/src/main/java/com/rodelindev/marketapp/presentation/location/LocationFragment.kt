package com.rodelindev.marketapp.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentLocationBinding

    private var map: GoogleMap? = null

    @SuppressLint("MissingPermission")
    val coarsePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> map?.isMyLocationEnabled = true
                else -> println("Permiso denegado")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        createMarker()
        enableLocation()
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    @SuppressLint("MissingPermission")
    private fun enableLocation() {

        map?.uiSettings?.isZoomControlsEnabled = true

        //if(!::map.isInitialized) return

        if (isLocationPermissionGranted()) {
            map?.isMyLocationEnabled = true
        } else {
            coarsePermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

    }

    private fun createMarker() {
        //-12.089798,-77.0556967
        val place = LatLng(-12.089798, -77.0556967)
        val marker = MarkerOptions().position(place).title("Plaza Salaverry")
        map?.addMarker(marker)
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 16f), 2000, null)
    }
}