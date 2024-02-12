package com.tes.global.data.api

import com.tes.global.model.request.LoginRequest
import com.tes.global.model.request.RegisterRequest
import okhttp3.RequestBody

class ApiRepository (private val apiInterface: ApiInterface) {

    suspend fun register(
        email: String,
        firstname: String,
        grup: String,
        hp: String,
        jenis_kelamin: Int,
        lastname: String,
        password: String,
        referral_code: String,
        role: String,
        strict_password: Boolean,
        tgl_lahir: String
    ) = apiInterface.register(
        RegisterRequest(
            email, firstname, grup, hp, jenis_kelamin, lastname, password, referral_code, role, strict_password, tgl_lahir
        )
    )

    suspend fun login(
        email: String,
        password: String
    ) = apiInterface.login(
        LoginRequest(
            email, password
        )
    )

    suspend fun getDashboard(
        fromDate: String,
        toDate: String
    ) = apiInterface.getDashboard(
        fromDate, toDate
    )

    suspend fun getProfileMe() = apiInterface.getProfileMe()

    suspend fun getBranchOffice() = apiInterface.getBranchOffice()

    suspend fun getType() = apiInterface.getType()

    suspend fun getProbability() = apiInterface.getProbability()

    suspend fun getStatus() = apiInterface.getStatus()

    suspend fun getChannel() = apiInterface.getChannel()

    suspend fun getMedia() = apiInterface.getMedia()

    suspend fun getSource() = apiInterface.getSource()

    suspend fun submitLead(request: RequestBody) = apiInterface.submitLead(request)

    suspend fun getLead() = apiInterface.getLead()

    suspend fun deleteLead(id_lead: String) = apiInterface.deleteLead(id_lead)

}