package org.mainsoft.mapsme.api.model

import com.google.gson.annotations.SerializedName


class GeocodingResponse {

    @SerializedName("type")
    lateinit var type: String

    @SerializedName("features")
    lateinit var features: List<Feature>
}