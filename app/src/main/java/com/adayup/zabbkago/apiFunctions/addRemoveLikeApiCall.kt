package com.adayup.zabbkago.apiFunctions
import com.adayup.zabbkago.interfaces.GetUserDetailsService
import com.adayup.zabbkago.responsesDataClasses.UserDetails

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.AddRemoveLikeApiService
import com.adayup.zabbkago.responsesDataClasses.AddRemoveLike

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private val PREFS_KEY = "prefs"


suspend fun addRemoveLikeApiCall(context: Context, shopID: Int): AddRemoveLike {
    val service = RetrofitClient.retrofitInstance.create(AddRemoveLikeApiService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<AddRemoveLike> = withContext(Dispatchers.IO) {
        service.addRemoveLike(shopID, apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return AddRemoveLike("Error", "Error in API call")
}