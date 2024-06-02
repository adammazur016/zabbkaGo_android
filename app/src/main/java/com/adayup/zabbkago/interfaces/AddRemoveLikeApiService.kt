package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.AddRemoveLike
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AddRemoveLikeApiService {
    @POST("/v1/shop/{shop_id}/like")
    suspend fun addRemoveLike(
        @Path("shop_id") shop_id: Int,
        @Query("session_token") session_token: String
    ): Response<AddRemoveLike>

}