package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Achievement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAcheivementsListService {
    @GET("/v1/achievements")
    suspend fun GetAcheivementsList(
        @Query("session_token") apiKey: String,
    ): Response<List<Achievement>> // Adjusted to expect a List<com.adayup.zabbkago.responsesDataClasses.Place> directly
}