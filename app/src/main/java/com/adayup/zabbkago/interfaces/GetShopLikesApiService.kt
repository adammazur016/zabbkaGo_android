package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.GetShopLikes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetShopLikesApiService {
    @GET("/v1/shop/{shopID}/likes")
    suspend fun getShopLikes(
        @Path("shopID") shopID: Int
    ): Response<GetShopLikes>
}