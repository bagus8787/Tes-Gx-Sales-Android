package com.tes.global.model.request

data class RegisterRequest(
    val email: String,
    val firstname: String,
    val grup: String,
    val hp: String,
    val jenis_kelamin: Int,
    val lastname: String,
    val password: String,
    val referral_code: String,
    val role: String,
    val strict_password: Boolean,
    val tgl_lahir: String
)