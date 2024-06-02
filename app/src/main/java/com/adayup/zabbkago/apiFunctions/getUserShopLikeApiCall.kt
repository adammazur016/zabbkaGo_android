package com.adayup.zabbkago.apiFunctions

import GetUserShopLikeApiService
import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.responsesDataClasses.GetUserShopLike
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun getUserShopLikeApiCall(context: Context, shopID: Int): GetUserShopLike {
    val service = RetrofitClient.retrofitInstance.create(GetUserShopLikeApiService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
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