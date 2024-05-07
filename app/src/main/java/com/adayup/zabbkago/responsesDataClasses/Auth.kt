package com.adayup.zabbkago.responsesDataClasses

data class Auth(
    val session_token: String,
    val status: String,
    val user_id: String
)