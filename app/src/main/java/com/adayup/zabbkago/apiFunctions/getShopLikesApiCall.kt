package com.adayup.zabbkago.apiFunctions


import com.adayup.zabbkago.responsesDataClasses.UserDetails

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetShopLikesApiService
import com.adayup.zabbkago.responsesDataClasses.GetShopLikes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
private var RANK_KEY = "rank"
suspend fun getShopLikes(context: Context, shopID: Int): GetShopLikes {
    val service = RetrofitClient.retrofitInstance.create(GetShopLikesApiService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val response: Response<GetShopLikes> = withContext(Dispatchers.IO) {
        service.getShopLikes(shopID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return GetShopLikes(-1,-1)
}