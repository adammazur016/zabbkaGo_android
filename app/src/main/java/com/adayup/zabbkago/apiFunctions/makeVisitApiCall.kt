package com.adayup.zabbkago.apiFunctions

import RetrofitClient
import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.responsesDataClasses.makeVisit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"

suspend fun makeVisitApiCall(context: Context, userID: Int, placeID: Int): String{
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<makeVisit> = withContext(Dispatchers.IO) {
        RetrofitClient.retrofitInstance.create(com.adayup.zabbkago.interfaces.MakeVisitApiCall::class.java)
            .MakeVisit(apiKey, userID, placeID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it.Status
        }
    }
    return "Visit aborted"
}