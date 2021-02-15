package com.github.nelayanapp.data.remote

import com.github.nelayanapp.BuildConfig
import com.github.nelayanapp.data.remote.model.Token
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getAccessToken (
        @Field("grant_type") grantType: String = "password",
        @Field("client_id") clientId: Int = BuildConfig.CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fcm_token") fcmToken: String
    ) : Response<Token>

    // Refresh access token
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun refreshAccessToken (
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: Int,
        @Field("client_secret") clientSecret: String,
        @Field("scope") scope: String
    ) : Response<Token>
}