package com.tes.global.model.response

data class ProfileMeResponse(
    val status: String,
    val message: String,
    val `data`: Data
) {
    data class Data(
        val id: Int,
        val name: String,
        val email: String
    )
}