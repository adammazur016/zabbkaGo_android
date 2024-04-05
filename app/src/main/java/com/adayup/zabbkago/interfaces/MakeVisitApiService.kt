package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.makeVisit
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface MakeVisitApiCall {
    @POST("makevisit")
    suspend fun MakeVisit(
        @Query("api_key") apiKey: String,
        @Query("user_id") userId: Int,
        @Query("place_id") placeID: Int
    ): Response<makeVisit> // Adjusted to expect a List<com.adayup.zabbkago.responsesDataClasses.Place> directly
}