package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Register
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterApiService {
    @POST("/v1/register")
    suspend fun Register(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Register>
}