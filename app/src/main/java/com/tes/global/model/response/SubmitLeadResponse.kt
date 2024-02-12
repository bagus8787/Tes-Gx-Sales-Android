package com.tes.global.model.response

data class SubmitLeadResponse(
    val status: String,
    val message: String,
    val `data`: Data
) {
    data class Data(
        val id: Int,
        val number: String,
        val fullName: String,
        val email: String,
        val phone: String,
        val address: String,
        val latitude: String,
        val longitude: String,
        val companyName: String,
        val gender: String,
        val IDNumber: String,
        val IDNumberPhoto: String,
        val generalNotes: String,
        val branchOffice: BranchOffice,
        val status: Status,
        val probability: Probability,
        val type: Type,
        val channel: Channel,
        val media: Media,
        val source: Source,
        val createdAt: String
    ) {
        data class BranchOffice(
            val id: Int,
            val name: String
        )

        data class Status(
            val id: Int,
            val name: String
        )

        data class Probability(
            val id: Int,
            val name: String
        )

        data class Type(
            val id: Int,
            val name: String
        )

        data class Channel(
            val id: Int,
            val name: String
        )

        data class Media(
            val id: Int,
            val name: String
        )

        data class Source(
            val id: Int,
            val name: String
        )
    }
}