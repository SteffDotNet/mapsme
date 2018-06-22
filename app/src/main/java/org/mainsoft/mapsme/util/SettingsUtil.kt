package org.mainsoft.mapsme.util

import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.geometry.LatLng

object SettingsUtil {

    private var STYLE_DEFAULT = Style.LIGHT
    private var ZOOM_DEFAULT = 0.0
    private var POSITION_LNG_DEFAULT = 30.2049
    private var POSITION_LAT_DEFAULT = 55.1904

    var style: String = STYLE_DEFAULT
    var zoom: Double = ZOOM_DEFAULT
    var position: LatLng = LatLng(POSITION_LAT_DEFAULT, POSITION_LNG_DEFAULT)

    fun applyDefaultSettings() {
        style = STYLE_DEFAULT
        zoom = ZOOM_DEFAULT
        position = LatLng(POSITION_LAT_DEFAULT, POSITION_LNG_DEFAULT)

        saveSettings()
    }

    fun saveSettings() {
        PrefsUtil.getEditor().putString(PrefsUtil.MAP_STYLE, style).commit()
        PrefsUtil.getEditor().putFloat(PrefsUtil.MAP_ZOOM, zoom.toFloat()).commit()
        PrefsUtil.getEditor().putFloat(PrefsUtil.MAP_POSITION_LAT, position.latitude.toFloat())
        PrefsUtil.getEditor().putFloat(PrefsUtil.MAP_POSITION_LNG, position.longitude.toFloat())
    }

    fun loadSettings() {
        style = PrefsUtil.getPreferences().getString(PrefsUtil.MAP_STYLE, STYLE_DEFAULT)
        zoom = PrefsUtil.getPreferences().getFloat(PrefsUtil.MAP_ZOOM, ZOOM_DEFAULT.toFloat()).toDouble()
        position.latitude = PrefsUtil.getPreferences().getFloat(PrefsUtil.MAP_POSITION_LAT, POSITION_LAT_DEFAULT.toFloat()).toDouble()
        position.longitude = PrefsUtil.getPreferences().getFloat(PrefsUtil.MAP_POSITION_LNG, POSITION_LNG_DEFAULT.toFloat()).toDouble()
    }
}