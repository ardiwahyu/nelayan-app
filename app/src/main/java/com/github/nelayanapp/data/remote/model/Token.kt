package com.github.nelayanapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token (
    @SerializedName("token_type") val tokenType: String?,
    @SerializedName("expires_in") val expiresIn: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?
): Parcelable