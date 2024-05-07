package com.adayup.zabbkago.interfaces


import com.adayup.zabbkago.responsesDataClasses.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetUserDetailsService {
    @GET("/v1/user/{userID}")
    suspend fun GetUserDetailsService(
        @Path("userID") userID: String
    ): Response<UserDetails>
}