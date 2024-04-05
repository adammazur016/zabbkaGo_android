package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.addRankApiService
import com.adayup.zabbkago.responsesDataClasses.AddRank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
suspend fun incrementRankApiCall(context: Context, userID: String): String {
    val service = RetrofitClient.retrofitInstance.create(addRankApiService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<AddRank> = withContext(Dispatchers.IO) {
        service.addRank(userID, apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {

            return it.Status
        }
    }
    return "Adding failed"
}