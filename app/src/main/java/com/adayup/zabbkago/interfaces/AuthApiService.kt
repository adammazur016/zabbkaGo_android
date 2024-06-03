package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Auth
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface authApiService {
    @POST("/v1/login")
    suspend fun getTodo(
        @Query("username") user: String,
        @Query("password") password: String
    ): Response<Auth>
}