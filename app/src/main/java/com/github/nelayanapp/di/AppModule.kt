package com.github.nelayanapp.di

import android.content.Context
import com.github.nelayanapp.BuildConfig.BASE_URL
import com.github.nelayanapp.BuildConfig.DEBUG
import com.github.nelayanapp.data.local.sp.SessionManager
import com.github.nelayanapp.data.local.sp.TokenHolder
import com.github.nelayanapp.data.remote.ApiServices
import com.github.nelayanapp.data.remote.authentication.AccessTokenAuthenticator
import com.github.nelayanapp.data.remote.authentication.AccessTokenInterceptor
import com.github.nelayanapp.data.remote.authentication.AccessTokenProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {
    private const val CONNECT_TIMEOUT: Long = 30
    private const val READ_TIMEOUT: Long = 30
    private const val WRITE_TIMEOUT: Long = 30

    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext _context: Context): ApiServices {
        val apiServices: ApiServices
        val client = OkHttpClient.Builder()
        val tokenHolder = TokenHolder(_context)
        val sessionManager = SessionManager(_context, tokenHolder)
        val accessTokenProvider = AccessTokenProviderImpl(tokenHolder, sessionManager)
        if(DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }
        client
            .addInterceptor(AccessTokenInterceptor(accessTokenProvider))
            .authenticator(AccessTokenAuthenticator(accessTokenProvider))
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        apiServices = retrofit.create(ApiServices::class.java)
        return apiServices
    }
}