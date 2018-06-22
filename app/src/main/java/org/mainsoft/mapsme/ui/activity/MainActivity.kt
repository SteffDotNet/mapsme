package org.mainsoft.mapsme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener
import org.mainsoft.mapsme.mvp.presenter.MapPresenter
import org.mainsoft.mapsme.mvp.view.MapsView
import org.mainsoft.mapsme.ui.activity.BaseActivity
import org.mainsoft.mapsme.util.SettingsUtil
import android.graphics.Color.parseColor
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.core.constants.Constants.PRECISION_5
import com.mapbox.geojson.LineString



class MainActivity : BaseActivity(), MapsView, OnMapClickListener {

    @InjectPresenter
    lateinit var presenter: MapPresenter

    @BindView(R.id.mapView)
    lateinit var mapView: MapView

    lateinit var mapBoxMap: MapboxMap
    val REQUEST_CODE = 666
    var markers : ArrayList<Marker> = arrayListOf()

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
        presenter.navigate(markers)
    }

    override fun drawRoute(route : DirectionsRoute) {
        /*val lineString = LineString.fromPolyline(route.geometry()!!, PRECISION_5)
        val positions = lineString.coordinates() ?: return

        for (i in positions!!.indices) {
            points.add(LatLng(
                    positions!!.get(i).getLatitude(),
                    positions!!.get(i).getLongitude()))
        }

        // Draw Points on MapView
        if (polyline != null) {
            mapboxMap.removePolyline(polyline)
            polyline = null
        }

        polyline = mapboxMap.addPolyline(PolylineOptions()
                .addAll(points)
                .color(Color.parseColor("#ff00ff"))
                .width(2f))*/
    }
}
