package com.adayup.zabbkago.interfaces


import com.adayup.zabbkago.responsesDataClasses.MakeVisit
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MakeVisitApiService {
    @POST("/v1/shop/{place_id}/visit")
    suspend fun MakeVisit(
        @Path("place_id") placeID: String,
        @Query("session_token") apiKey: String
    ): Response<MakeVisit>
}