package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Achievement
import com.adayup.zabbkago.responsesDataClasses.AchievementID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetAcheivementsListService {
    @GET("/v1/user/{user_id}/achievements")
    suspend fun GetAcheivementsList(
        @Path("user_id") user_id: Int,
    ): Response<List<AchievementID>>
}