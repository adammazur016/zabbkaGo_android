package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetAcheivementsListService
import com.adayup.zabbkago.interfaces.GetPlacesApiService
import com.adayup.zabbkago.responsesDataClasses.Achievement
import com.adayup.zabbkago.responsesDataClasses.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
suspend fun getAchievementsListApiCall(context: Context): List<Achievement>{
    val service = RetrofitClient.retrofitInstance.create(GetAcheivementsListService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
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