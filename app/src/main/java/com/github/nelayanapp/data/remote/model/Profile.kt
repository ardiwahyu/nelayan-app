package com.github.nelayanapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Profile (
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("no_hp") val noHp: String?,
    @SerializedName("nik") val nik: String?,
    @SerializedName("foto") val foto: String?
): Parcelable