package com.adayup.zabbkago

import makeVisit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface makeVisitApiCall {
    @POST("makevisit")
    suspend fun MakeVisit(
        @Query("api_key") apiKey: String,
        @Query("user_id") userId: Int,
        @Query("place_id") placeID: Int
    ): Response<makeVisit> // Adjusted to expect a List<Place> directly
}