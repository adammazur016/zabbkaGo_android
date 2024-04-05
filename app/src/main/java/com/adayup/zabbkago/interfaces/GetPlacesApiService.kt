package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Place
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface GetPlacesApiService {
    @POST("test")
    suspend fun GetPlaces(
        @Query("api_key") apiKey: String,
    ): Response<List<Place>> // Adjusted to expect a List<com.adayup.zabbkago.responsesDataClasses.Place> directly
}