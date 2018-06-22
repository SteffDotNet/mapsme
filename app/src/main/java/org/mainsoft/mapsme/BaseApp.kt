package org.mainsoft.mapsme

import android.app.Application
import android.content.Context
import com.mapbox.mapboxsdk.Mapbox

class BaseApp : Application() {

    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = this
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mb_access_token))
    }
}