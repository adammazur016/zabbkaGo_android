package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetVisitedPlaceService
import com.adayup.zabbkago.responsesDataClasses.VisitedPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun getVisitedPlacesListApiCall(context: Context): List<VisitedPlace> {
    val service = RetrofitClient.retrofitInstance.create(GetVisitedPlaceService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val userID = sharedPreferences.getString(keys.ID_KEY, null).toString()
    val response: Response<List<VisitedPlace>> = withContext(Dispatchers.IO) {
        service.GetVisitedPlaces(userID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}