package org.mainsoft.mapsme.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import org.mainsoft.mapsme.BaseApp
import org.mainsoft.mapsme.R
import org.mainsoft.mapsme.mvp.view.MapsView
import org.mainsoft.mapsme.util.SettingsUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class MapPresenter : BasePresenter<MapsView>() {

    var navigation = MapboxNavigation(BaseApp.context, BaseApp.context.getString(R.string.mb_access_token))

    fun update() {
        SettingsUtil.loadSettings()
        viewState.updateMap()
    }

    fun addMarker(latLng: LatLng) {
        viewState.addMarker(latLng)
    }

    fun navigate(markers: ArrayList<Marker>) {
        if (markers.size < 2) {
            return
        }
        var origin = getPointFormMarker(markers[0])
        var destination = getPointFormMarker(markers[1])
        var points = getPointsFromMarkers(markers)

        var builder = NavigationRoute.builder(BaseApp.context)
                .accessToken(BaseApp.context.getString(R.string.mb_access_token))
                .origin(origin)
                .destination(destination)
        for (point in points) {
            builder.addWaypoint(point)
        }
        builder.build().getRoute(object : Callback<DirectionsResponse> {
            override fun onFailure(call: Call<DirectionsResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<DirectionsResponse>?, response: Response<DirectionsResponse>) {
                if (response.body() == null) {
                    return
                }
                var routes = response.body()?.routes()
                if(routes == null){
                    Log.i("MapBox", "No routes")
                    return
                }
                viewState.drawRoute(routes[0])
            }
        })

    }

    fun getPointFormMarker(marker: Marker): Point {
        return Point.fromLngLat(marker.position.longitude, marker.position.latitude)
    }

    fun getPointsFromMarkers(markers: ArrayList<Marker>): ArrayList<Point> {
        var points = arrayListOf<Point>()
        markers.removeAt(0)
        markers.removeAt(markers.size - 1)
        for (marker in markers) {
            points.add(Point.fromLngLat(marker.position.longitude, marker.position.latitude))
        }
        return points
    }
}