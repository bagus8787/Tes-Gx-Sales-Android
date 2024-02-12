package com.tes.global.model.response

data class LoginResponse(
    val status: String,
    val token: String,
    val type: String
)