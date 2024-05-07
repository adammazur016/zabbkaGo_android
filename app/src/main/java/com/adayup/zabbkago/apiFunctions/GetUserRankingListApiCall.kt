package com.adayup.zabbkago.apiFunctions


import com.adayup.zabbkago.interfaces.GetUserDetailsService
import com.adayup.zabbkago.responsesDataClasses.UserDetails

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetUserRankingListService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
private var RANK_KEY = "rank"
suspend fun GetUserRankingListApiCall(context: Context): List<UserDetails> {
    val service = RetrofitClient.retrofitInstance.create(GetUserRankingListService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<List<UserDetails>> = withContext(Dispatchers.IO) {
        service.GetUserRankingList(apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}