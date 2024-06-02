package com.adayup.zabbkago.apiFunctions

import com.adayup.zabbkago.interfaces.GetPlaceDetailService
import com.adayup.zabbkago.responsesDataClasses.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun getPlaceDetailApiCall(shopID: Int): Place{
    val service = RetrofitClient.retrofitInstance.create(GetPlaceDetailService::class.java)
    val response: Response<Place> = withContext(Dispatchers.IO) {
        service.GetPlaceDetail(shopID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return Place("",0,0.0,0.0,"")
}