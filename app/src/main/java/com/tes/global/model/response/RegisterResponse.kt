package com.tes.global.model.response

data class RegisterResponse(
    val `data`: Data,
    val status: Status
) {
    data class Data(
        val email: String,
        val firstname: String,
        val grup: String,
        val hp: String,
        val id: Int,
        val lastname: String,
        val ref_by: Any?,
        val status: Status
    ) {
        data class Status(
            val keterangan: String,
            val kode: Int
        )
    }

    data class Status(
        val keterangan: String,
        val kode: String
    )
}