package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetPlaceDetailService {
    @GET("/v1/shop/{shopId}")
    suspend fun GetPlaceDetail(
        @Path("shopId") shopId: Int,
    ): Response<Place> // Adjusted to expect a List<com.adayup.zabbkago.responsesDataClasses.Place> directly
}