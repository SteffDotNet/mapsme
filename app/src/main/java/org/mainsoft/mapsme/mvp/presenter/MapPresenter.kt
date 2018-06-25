package org.mainsoft.mapsme.mvp.presenter

import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.mainsoft.mapsme.BaseApp
import org.mainsoft.mapsme.R
import org.mainsoft.mapsme.api.ApiService
import org.mainsoft.mapsme.api.MapApi
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

    fun findRoute(markers: ArrayList<Marker>) {
        if (markers.size < 2) {
            return
        }
        ApiService.getInstance().create(MapApi::class.java).findRoute(getCoordinates(markers),BaseApp.context.getString(R.string.mb_access_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res -> viewState.drawRoute(res)
                }

    }

    fun findPlace(point: LatLng){
        var position = "${point.longitude},${point.latitude}"
        var type = "address"
        ApiService.getInstance().create(MapApi::class.java).geocode(position, type, BaseApp.context.getString(R.string.mb_access_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{res ->
                    if(!res.features.isEmpty()){
                        Toast.makeText(BaseApp.context, res.features.get(0).placeName, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(BaseApp.context, "Place not found!", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun getCoordinates(markers: ArrayList<Marker>): String {
        var coordinates = StringBuilder()
        for (marker in markers) {
            coordinates.append("${marker.position.longitude},${marker.position.latitude};")
        }
        coordinates.deleteCharAt(coordinates.length-1)
        return coordinates.toString()
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