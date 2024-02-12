package com.tes.global.model.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
class AddLeadsRequest (
    val branchOfficeId: String,
    val probabilityId: String,
    val typeId: String,
    val channelId: String,
    val mediaId: String,
    val sourceId: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val companyName: String,
    val generalNotes: String,
    val gender: String,
    val IDNumber: String,
    val fileIdentity: File?= null
): Parcelable