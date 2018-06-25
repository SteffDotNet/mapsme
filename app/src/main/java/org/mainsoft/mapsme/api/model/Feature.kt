package org.mainsoft.mapsme.api.model

import com.google.gson.annotations.SerializedName



/**
 * Created by egor.stepanov on 25.06.2018.
 */
class Feature {

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("type")
    lateinit var type: String

    @SerializedName("text")
    lateinit var text: String

    @SerializedName("place_name")
    lateinit var placeName: String

    @SerializedName("address")
    lateinit var address: String

}