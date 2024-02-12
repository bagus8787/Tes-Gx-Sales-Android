package com.tes.global.data.api

import com.tes.global.model.request.LoginRequest
import com.tes.global.model.request.RegisterRequest
import com.tes.global.model.response.BranchOfficeResponse
import com.tes.global.model.response.DashboardResponse
import com.tes.global.model.response.LeadResponse
import com.tes.global.model.response.LoginResponse
import com.tes.global.model.response.ProfileMeResponse
import com.tes.global.model.response.RegisterResponse
import com.tes.global.model.response.SubmitLeadResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("users")
    fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("leads/dashboard")
    suspend fun getDashboard(
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String
    ): DashboardResponse

    @GET("profile")
    suspend fun getProfileMe(): ProfileMeResponse

    @GET("settings/branch-offices")
    suspend fun getBranchOffice(): BranchOfficeResponse

    @GET("settings/types")
    suspend fun getType(): BranchOfficeResponse

    @GET("settings/probabilities")
    suspend fun getProbability(): BranchOfficeResponse

    @GET("settings/statuses")
    suspend fun getStatus(): BranchOfficeResponse

    @GET("settings/channels")
    suspend fun getChannel(): BranchOfficeResponse

    @GET("settings/medias")
    suspend fun getMedia(): BranchOfficeResponse

    @GET("settings/sources")
    suspend fun getSource(): BranchOfficeResponse

    @POST("leads")
    suspend fun submitLead(
        @Body request: RequestBody
    ): SubmitLeadResponse

    @GET("leads")
    suspend fun getLead(): LeadResponse

    @DELETE("leads/{id_lead}")
    suspend fun deleteLead(
        @Path("id_lead") id_lead: String
    ): LeadResponse

}