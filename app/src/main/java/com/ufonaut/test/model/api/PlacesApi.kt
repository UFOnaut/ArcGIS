package com.ufonaut.test.model.api

import android.location.Location
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.LocatorTask
import com.ufonaut.test.ext.toPlace
import com.ufonaut.test.model.dto.Place
import kotlin.coroutines.suspendCoroutine

class PlacesApiImpl(): PlacesApi {
    override fun getNearestPlaces(location: Location, callback: PlacesCallback) {
        val places = mutableListOf<Place>()
        val locatorTask =
            LocatorTask("https://geocode-api.arcgis.com/arcgis/rest/services/World/GeocodeServer")

        val geocodeParameters = GeocodeParameters().apply {
            categories.add("food")
            preferredSearchLocation = Point(location.latitude, location.longitude, SpatialReferences.getWgs84())
            outputLanguageCode = "uk"
            maxResults = 20
        }

        val geocodeResultsFuture = locatorTask.geocodeAsync("", geocodeParameters)

        geocodeResultsFuture.addDoneListener {
            try {
                val geocodeResults = geocodeResultsFuture.get()

                geocodeResults.forEach {
                    places.add(it.toPlace())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            callback.onPlacesReceived(places)
        }
    }
}

interface PlacesApi {
    fun getNearestPlaces(location: Location, callback: PlacesCallback)
}

interface PlacesCallback {
    fun onPlacesReceived(places: List<Place>)
}