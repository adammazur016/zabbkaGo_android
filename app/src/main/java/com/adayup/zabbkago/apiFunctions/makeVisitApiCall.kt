package com.adayup.zabbkago.apiFunctions
import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.MakeVisitApiService
import com.adayup.zabbkago.responsesDataClasses.MakeVisit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun makeVisitApiCall(context: Context, placeID: String): MakeVisit{
    val service = RetrofitClient.retrofitInstance.create(MakeVisitApiService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
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