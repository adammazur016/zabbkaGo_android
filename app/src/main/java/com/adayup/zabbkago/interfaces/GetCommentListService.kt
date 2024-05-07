package com.adayup.zabbkago.interfaces

import com.adayup.zabbkago.responsesDataClasses.Comment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetCommentListService {
    @GET("/v1/shop/{shopID}/comments")
    suspend fun GetCommentList(
        @Path("shopID") shopID: Int,
    ): Response<List<Comment>> // Adjusted to expect a List<com.adayup.zabbkago.responsesDataClasses.Place> directly
}