package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.AddComment
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AddCommentService {

    @POST("/v1/shop/{shopID}/comment")
    suspend fun addComment(
        @Path("shopID") shopID: String,
        @Query("session_token") api_key: String,
        @Query("content") content: String,
        @Query("parent_id") parent_id: Int? = null
    ): Response<AddComment>
}