package com.adayup.zabbkago.apiFunctions
import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetPlacesApiService
import com.adayup.zabbkago.interfaces.MakeVisitApiService
import com.adayup.zabbkago.responsesDataClasses.MakeVisit
import com.adayup.zabbkago.responsesDataClasses.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
suspend fun makeVisitApiCall(context: Context, placeID: String): MakeVisit{
    val service = RetrofitClient.retrofitInstance.create(MakeVisitApiService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<MakeVisit> = withContext(Dispatchers.IO) {
        service.MakeVisit(placeID, apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {

            return it
        }
    }
    return MakeVisit("","")
}