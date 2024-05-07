package com.adayup.zabbkago.apiFunctions

import com.adayup.zabbkago.interfaces.GetCommentListService
import com.adayup.zabbkago.responsesDataClasses.Comment
import com.adayup.zabbkago.responsesDataClasses.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun getCommentListApiCall(shopID: Int): List<Comment> {
    val service = RetrofitClient.retrofitInstance.create(GetCommentListService::class.java)
    val response: Response<List<Comment>> = withContext(Dispatchers.IO) {
        service.GetCommentList(shopID)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return emptyList()
}