package org.mainsoft.mapsme

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mapbox.core.constants.Constants.PRECISION_5
import com.mapbox.geojson.LineString
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.annotations.Polyline
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener
import org.mainsoft.mapsme.api.model.DirectionResponse
import org.mainsoft.mapsme.mvp.presenter.MapPresenter
import org.mainsoft.mapsme.mvp.view.MapsView
import org.mainsoft.mapsme.ui.activity.BaseActivity
import org.mainsoft.mapsme.util.SettingsUtil

class MainActivity : BaseActivity(), MapsView, OnMapClickListener {

    @InjectPresenter
    lateinit var presenter: MapPresenter

    @BindView(R.id.mapView)
    lateinit var mapView: MapView

    lateinit var mapBoxMap: MapboxMap
    val REQUEST_CODE = 666
    var markers : ArrayList<Marker> = arrayListOf()
    var polyline: Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        mapView.getMapAsync {
            mapBoxMap = it
            mapBoxMap.addOnMapClickListener(this)
            presenter.update()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.itemSettings -> openSettings()
        }
        return true
    }

    fun openSettings(){
        startActivityForResult(Intent(this@MainActivity, SettingsActivity::class.java), REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            presenter.update()
        }
    }

    override fun onMapClick(point: LatLng) {
        presenter.addMarker(point)
    }

    override fun updateMap() {
        mapBoxMap.setStyle(SettingsUtil.style)
        mapBoxMap.cameraPosition = CameraPosition.Builder()
                .target(SettingsUtil.position)
                .zoom(SettingsUtil.zoom)
                .build()
    }

    override fun addMarker(latLng: LatLng) {
        markers.add(mapBoxMap.addMarker(MarkerOptions().position(latLng).title("Marker ${markers.size + 1}")))
        presenter.findPlace(latLng)
        presenter.findRoute(markers)
    }

    override fun drawRoute(result : DirectionResponse) {
        if(result.trips.isEmpty()){
            return
        }
        val lineString = LineString.fromPolyline(result.trips[0].geometry, PRECISION_5)
        val positions = lineString.coordinates()

        var points = arrayListOf<LatLng>()
        for (i in positions.indices) {
            points.add(LatLng(
                    positions.get(i).latitude(),
                    positions.get(i).longitude()))
        }

        // Draw Points on MapView
        if (polyline != null) {
            mapBoxMap.removePolyline(polyline!!)
        }

        polyline = mapBoxMap.addPolyline(PolylineOptions()
                .addAll(points)
                .color(Color.parseColor("#ff00ff"))
                .width(2f))
    }
}
