package com.adayup.zabbkago.apiFunctions

import com.adayup.zabbkago.interfaces.GetAllAchievementListService
import com.adayup.zabbkago.responsesDataClasses.Achievement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun getAllAchievementListApiCall(): List<Achievement>{
    val service = RetrofitClient.retrofitInstance.create(GetAllAchievementListService::class.java)
    val response: Response<List<Achievement>> = withContext(Dispatchers.IO) {
        service.GetAllAchievementList()
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}