package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetPlacesApiService
import com.adayup.zabbkago.responsesDataClasses.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun getPlacesApiCall(context: Context): List<Place>{
    val service = RetrofitClient.retrofitInstance.create(GetPlacesApiService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
    val response: Response<List<Place>> = withContext(Dispatchers.IO) {
        service.GetPlaces(apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {

            return it
        }
    }
    return emptyList()
}