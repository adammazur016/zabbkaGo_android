package com.adayup.zabbkago.apiFunctions

import android.content.Context
import android.content.SharedPreferences
import com.adayup.zabbkago.interfaces.AddCommentService

import com.adayup.zabbkago.responsesDataClasses.AddComment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import sharedKeys


private lateinit var sharedPreferences: SharedPreferences
private val keys = sharedKeys()
suspend fun addCommentApiCall(context: Context, content: String, shopID: Int, parentID: Int): AddComment {
    val service = RetrofitClient.retrofitInstance.create(AddCommentService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
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

suspend fun addCommentApiCall(context: Context, content: String, shopID: Int): AddComment {
    val service = RetrofitClient.retrofitInstance.create(AddCommentService::class.java)
    sharedPreferences = context.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
    val apiKey = sharedPreferences.getString(keys.API_KEY, null).toString()
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