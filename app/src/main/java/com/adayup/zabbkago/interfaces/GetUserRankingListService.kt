package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserRankingListService {
    @GET("/v1/users")
    suspend fun GetUserRankingList(
        @Query("token") apiKey: String
    ): Response<List<UserDetails>>
}