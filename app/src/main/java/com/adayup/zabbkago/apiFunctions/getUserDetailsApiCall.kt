package com.adayup.zabbkago.apiFunctions

import com.adayup.zabbkago.interfaces.GetUserDetailsService
import com.adayup.zabbkago.responsesDataClasses.UserDetails

import android.content.Context
import android.content.SharedPreferences

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
private var RANK_KEY = "rank"
var ID_KEY = "id"
suspend fun getUserDetailsApiCall(context: Context, userID: String): UserDetails {
    val service = RetrofitClient.retrofitInstance.create(GetUserDetailsService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val response: Response<UserDetails> = withContext(Dispatchers.IO) {
        service.GetUserDetailsService(userID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(RANK_KEY, it.points.toString())
            editor.apply()
            return it
        }
    }
    return UserDetails(-1,"",-1)
}