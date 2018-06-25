package org.mainsoft.mapsme.api.model

import com.google.gson.annotations.SerializedName

class DirectionResponse {

    @SerializedName("code")
    lateinit var code: String

    @SerializedName("trips")
    lateinit var trips: List<Trip>
}