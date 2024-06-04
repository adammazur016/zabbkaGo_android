package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Achievement
import com.adayup.zabbkago.responsesDataClasses.AchievementID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetAllAchievementListService {
    @GET("/v1/achievements")
    suspend fun GetAllAchievementList(): Response<List<Achievement>>
}