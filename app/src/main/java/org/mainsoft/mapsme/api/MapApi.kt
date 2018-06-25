package org.mainsoft.mapsme.api

import io.reactivex.Observable
import org.mainsoft.mapsme.api.model.DirectionResponse
import org.mainsoft.mapsme.api.model.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapApi {

    @GET("/geocoding/v5/mapbox.places/{position}.json")
    fun geocode(@Path("position") pos: String, @Query("types") type: String, @Query("access_token") token: String): Observable<GeocodingResponse>

    @GET("optimized-trips/v1/mapbox/driving/{coordinates}")
    fun findRoute(@Path("coordinates") coordinates: String, @Query("access_token") token: String): Observable<DirectionResponse>
}