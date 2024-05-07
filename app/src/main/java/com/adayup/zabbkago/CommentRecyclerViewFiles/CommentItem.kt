package com.adayup.zabbkago.CommentRecyclerViewFiles

data class CommentItem(
    val id: Int,
    val content: String,
    val nickname: String,
    val shopID: Int,
    val parentID: Int
)