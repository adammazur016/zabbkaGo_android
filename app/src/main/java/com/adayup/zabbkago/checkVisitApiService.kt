package com.adayup.zabbkago

import checkVisit
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface checkVisitApiService {
    @POST("checkvisit")
    suspend fun getVisitApiService(
        @Query("api_key") apiKey: String,
        @Query("user_id") userId: Int,
        @Query("place_id") placeID: Int
    ): Response<checkVisit> // Adjusted to expect a List<Place> directly
}

