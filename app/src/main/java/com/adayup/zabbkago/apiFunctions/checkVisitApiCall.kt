package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.checkVisitApiService
import com.adayup.zabbkago.responsesDataClasses.checkVisit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"

suspend fun checkVisitApiCall(context: Context, userID: Int, placeID: Int): String{
    val service = RetrofitClient.retrofitInstance.create(checkVisitApiService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<checkVisit> = withContext(Dispatchers.IO) {
        service.getVisitApiService(apiKey, userID, placeID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {

            return it.Status
        }
    }
    return "Visit impossible"
}