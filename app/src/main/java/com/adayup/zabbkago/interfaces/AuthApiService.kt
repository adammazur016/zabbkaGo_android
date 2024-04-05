package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Auth
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface authApiService {
    @POST("login")
    suspend fun getTodo(
        @Query("user") user: String,
        @Query("password") password: String
    ): Response<Auth> // Ensure com.adayup.zabbkago.responsesDataClasses.Auth is correctly defined to match the expected response
}