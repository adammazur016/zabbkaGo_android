package com.adayup.zabbkago.apiFunctions
import com.adayup.zabbkago.responsesDataClasses.UserDetails
import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.GetUserRankingListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys

private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun getUserRankingListApiCall(context: Context): List<UserDetails> {
    val service = RetrofitClient.retrofitInstance.create(GetUserRankingListService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
    val response: Response<List<UserDetails>> = withContext(Dispatchers.IO) {
        service.GetUserRankingList(apiKey)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}