package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetUserRankingListService
import com.adayup.zabbkago.interfaces.GetVisitedPlaceService
import com.adayup.zabbkago.responsesDataClasses.UserDetails
import com.adayup.zabbkago.responsesDataClasses.VisitedPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
private var RANK_KEY = "rank"
suspend fun getVisitedPlacesListApiCall(context: Context): List<VisitedPlace> {
    val service = RetrofitClient.retrofitInstance.create(GetVisitedPlaceService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val userID = sharedPreferences.getString(ID_KEY, null).toString()
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