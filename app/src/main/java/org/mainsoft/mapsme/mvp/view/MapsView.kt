package org.mainsoft.mapsme.mvp.view

import com.arellomobile.mvp.MvpView
import com.mapbox.mapboxsdk.geometry.LatLng
import org.mainsoft.mapsme.api.model.DirectionResponse

interface MapsView : MvpView {

    fun updateMap()

    fun addMarker(latLng: LatLng)

    fun drawRoute(res: DirectionResponse)
}