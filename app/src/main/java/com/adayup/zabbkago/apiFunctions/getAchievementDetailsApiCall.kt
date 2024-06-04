package com.adayup.zabbkago.apiFunctions

import GetAchievementDetailsService
import android.content.Context
import com.adayup.zabbkago.responsesDataClasses.Achievement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun getAchievementDetailsApiCall(context: Context, achievementID: Int): Achievement{
    val service = RetrofitClient.retrofitInstance.create(GetAchievementDetailsService::class.java)
    val response: Response<Achievement> = withContext(Dispatchers.IO) {
        service.GetAcheivementsDetails(achievementID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return Achievement("Error", 0, "Error")
}