package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.AddRank
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface addRankApiService {
    @POST("addrankpoint")
    suspend fun addRank(
        @Query("api_key") api_key: String
    ): Response<AddRank>

}