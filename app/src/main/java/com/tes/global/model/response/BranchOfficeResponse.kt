package com.tes.global.model.response

data class BranchOfficeResponse(
    val status: String,
    val message: String,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val name: String
    )
}