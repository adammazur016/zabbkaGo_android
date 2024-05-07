package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetPlaceDetailService
import com.adayup.zabbkago.interfaces.GetPlacesApiService
import com.adayup.zabbkago.responsesDataClasses.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
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