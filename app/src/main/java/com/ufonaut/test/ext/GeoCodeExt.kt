package com.ufonaut.test.ext

import com.esri.arcgisruntime.tasks.geocode.GeocodeResult
import com.ufonaut.test.model.dto.Place

fun GeocodeResult.toPlace(): Place {
    val place = Place()
    place.title = label
    place.lat = displayLocation.x
    place.lng = displayLocation.y
    return place
}