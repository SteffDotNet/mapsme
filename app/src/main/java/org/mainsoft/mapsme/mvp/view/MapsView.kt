package org.mainsoft.mapsme.mvp.view

import com.arellomobile.mvp.MvpView
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.mapboxsdk.geometry.LatLng

interface MapsView : MvpView {

    fun updateMap()

    fun addMarker(latLng: LatLng)

    fun drawRoute(route: DirectionsRoute)
}