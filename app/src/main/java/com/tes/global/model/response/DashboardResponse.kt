package com.tes.global.model.response

data class DashboardResponse(
    val status: String,
    val message: String,
    val `data`: Data
) {
    data class Data(
        val total: Int,
        val statuses: List<Statuse>
    ) {
        data class Statuse(
            val name: String,
            val total: Int
        )
    }
}