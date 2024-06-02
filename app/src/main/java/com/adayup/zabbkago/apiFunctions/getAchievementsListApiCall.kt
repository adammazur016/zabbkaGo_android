package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetAcheivementsListService
import com.adayup.zabbkago.responsesDataClasses.Achievement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun getAchievementsListApiCall(context: Context): List<Achievement>{
    val service = RetrofitClient.retrofitInstance.create(GetAcheivementsListService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
    val response: Response<List<Achievement>> = withContext(Dispatchers.IO) {
        service.GetAcheivementsList(apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}