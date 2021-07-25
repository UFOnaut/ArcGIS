package com.ufonaut.test.ui.fragment

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ufonaut.test.model.dto.Place
import com.ufonaut.test.utils.DialogUtils
import com.ufonaut.test.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.RealmResults
import javax.inject.Inject

@SuppressLint("MissingPermission")
@AndroidEntryPoint
class CustomMapFragment: SupportMapFragment(), OnMapReadyCallback, GoogleMap.CancelableCallback,
    GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap
    private var location: Location? = null

    @Inject
    lateinit var viewModel: MainViewModel

    private val locationProvider: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerClickListener(this)
        map.isMyLocationEnabled = true
        viewModel.observePlaces().observe(viewLifecycleOwner, Observer {
            updateMarkers(googleMap, it)
        })
        askForLocation()
    }

    private fun askForLocation() {
        locationProvider.lastLocation.addOnSuccessListener { location ->
            this.location = location
            location?.let {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 14f), this)
            }
        }
    }

    private fun updateMarkers(googleMap: GoogleMap, places: RealmResults<Place>) {
        googleMap.clear()
        places.forEach { place ->
            if (place.lat != null && place.lng != null) {
                val position = LatLng(place.lat!!, place.lng!!)
                googleMap.addMarker(MarkerOptions().position(position).title(place.title))
            }
        }

    }

    override fun onFinish() {
        viewModel.refreshPlaces(location)
    }

    override fun onCancel() {
        viewModel.refreshPlaces(location)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val title = marker.title ?: "No title"
        val positionMessage = "${marker.position.latitude}, ${marker.position.longitude}"
        DialogUtils.showPlaceDetailsDialog(requireContext(), title, positionMessage)
        return true
    }

}