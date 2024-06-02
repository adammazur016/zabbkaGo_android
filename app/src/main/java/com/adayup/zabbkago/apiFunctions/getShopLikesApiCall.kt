package com.adayup.zabbkago.apiFunctions

import android.content.Context
import com.adayup.zabbkago.interfaces.GetShopLikesApiService
import com.adayup.zabbkago.responsesDataClasses.GetShopLikes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

suspend fun getShopLikes(context: Context, shopID: Int): GetShopLikes {
    val service = RetrofitClient.retrofitInstance.create(GetShopLikesApiService::class.java)
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