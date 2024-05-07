package com.adayup.zabbkago.responsesDataClasses

data class Comment (
    val content: String,
    val creation_time: String,
    val id: Int,
    val parent_id: Int,
    val place_id: Int,
    val user_id: Int
)
