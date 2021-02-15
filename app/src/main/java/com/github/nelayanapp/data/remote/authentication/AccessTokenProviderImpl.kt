package com.github.nelayanapp.data.remote.authentication

import com.github.nelayanapp.BuildConfig
import com.github.nelayanapp.data.local.sp.SessionManager
import com.github.nelayanapp.data.local.sp.TokenHolder
import com.github.nelayanapp.data.remote.ApiServices
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AccessTokenProviderImpl(private val tokenHolder: TokenHolder, private val sessionManager: SessionManager) :
    AccessTokenProvider {

    private val interceptor = HttpLoggingInterceptor()
    private val apiServices: ApiServices

    init {
        if(BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        apiServices = retrofit.create(ApiServices::class.java)
    }

    override fun token(): String? {
        return tokenHolder.getToken()?.accessToken
    }

    override fun refreshToken(): Boolean = runBlocking { tryRefresh() }

    private suspend fun tryRefresh(): Boolean {
        var result = false
        try {
            val response = apiServices.refreshAccessToken(
                "refresh_token",
                tokenHolder.getToken()?.refreshToken!!,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                ""
            )
            result = if (response.code() == 200) {
                response.body()?.let { tokenHolder.storeToken(it) }
                true
            } else {
                if(sessionManager.isLoggedIn)
                    sessionManager.logoutUser()
                false
            }
        } catch (e: Exception) {
//            SentryErrorReport.sentryCapture(e, null)
            e.printStackTrace()
        }
        return result
    }
}