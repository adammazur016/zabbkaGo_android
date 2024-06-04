package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetAcheivementsListService
import com.adayup.zabbkago.responsesDataClasses.Achievement
import com.adayup.zabbkago.responsesDataClasses.AchievementID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

suspend fun getAchievementsListApiCall(context: Context, userID: Int): List<AchievementID>{
    val service = RetrofitClient.retrofitInstance.create(GetAcheivementsListService::class.java)
    val response: Response<List<AchievementID>> = withContext(Dispatchers.IO) {
        service.GetAcheivementsList(userID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}