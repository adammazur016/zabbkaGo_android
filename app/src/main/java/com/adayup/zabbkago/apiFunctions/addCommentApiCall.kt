package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.AddCommentService

import com.adayup.zabbkago.interfaces.GetVisitedPlaceService
import com.adayup.zabbkago.responsesDataClasses.AddComment
import com.adayup.zabbkago.responsesDataClasses.AddRank
import com.adayup.zabbkago.responsesDataClasses.VisitedPlace

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


private lateinit var sharedPreferences: SharedPreferences
private val API_KEY = "api_key"
private var PREFS_KEY = "prefs"
private var RANK_KEY = "rank"
suspend fun addCommentApiCall(context: Context, content: String, shopID: String, parentID: Int): AddComment {
    val service = RetrofitClient.retrofitInstance.create(AddCommentService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<AddComment> = withContext(Dispatchers.IO) {
        service.addComment(shopID, apiKey, content, parentID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return AddComment("error in api call")
}

suspend fun addCommentApiCall(context: Context, content: String, shopID: String): AddComment {
    val service = RetrofitClient.retrofitInstance.create(AddCommentService::class.java)
    sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(API_KEY, null).toString()
    val response: Response<AddComment> = withContext(Dispatchers.IO) {
        service.addComment(shopID, apiKey, content)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return AddComment("error in api call")
}