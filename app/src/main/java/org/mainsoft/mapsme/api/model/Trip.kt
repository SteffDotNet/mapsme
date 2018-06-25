package org.mainsoft.mapsme.api.model

import com.google.gson.annotations.SerializedName



/**
 * Created by egor.stepanov on 25.06.2018.
 */
class Trip {

    @SerializedName("distance")
    var distance: Double = 0.0

    @SerializedName("duration")
    var duration: Double = 0.0

    @SerializedName("geometry")
    lateinit var geometry: String

}