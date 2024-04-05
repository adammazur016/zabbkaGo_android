package com.adayup.zabbkago.responsesDataClasses

data class Auth(
    val auth: String,
    val id: String?,
    val api_key: String?,
    val rank_points: Int?
)