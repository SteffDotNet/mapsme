package org.mainsoft.mapsme.util

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import org.mainsoft.mapsme.BaseApp

object PrefsUtil {
    var PREFERENCES_NAME = "mapsme_prefs"
    var MAP_STYLE = "style"
    var MAP_ZOOM = "zoom"
    var MAP_POSITION_LNG = "lng"
    var MAP_POSITION_LAT = "lat"

    fun getEditor(): Editor = BaseApp.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()

    fun getPreferences(): SharedPreferences = BaseApp.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}