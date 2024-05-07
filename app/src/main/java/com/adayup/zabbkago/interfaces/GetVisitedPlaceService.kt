package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.VisitedPlace
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GetVisitedPlaceService {
    @GET("/v1/user/{user_id}/visits")
    suspend fun GetVisitedPlaces(
        @Path("user_id") userID: String,
    ): Response<List<VisitedPlace>> // Adjusted to expect a List<com.adayup.zabbkago.responsesDataClasses.Place> directly
}