package com.adayup.zabbkago.apiFunctions

import GetUserShopLikeApiService
import com.adayup.zabbkago.responsesDataClasses.UserDetails

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetUserRankingListService
import com.adayup.zabbkago.responsesDataClasses.GetUserShopLike

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
private var RANK_KEY = "rank"
suspend fun getUserShopLikeApiCall(context: Context, shopID: Int): GetUserShopLike {
    val service = RetrofitClient.retrofitInstance.create(GetUserShopLikeApiService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<GetUserShopLike> = withContext(Dispatchers.IO) {
        service.GetUserShopLike(shopID, apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return GetUserShopLike("Error", "Error in API call")
}