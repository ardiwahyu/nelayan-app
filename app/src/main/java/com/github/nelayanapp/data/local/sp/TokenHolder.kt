package com.github.nelayanapp.data.local.sp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import com.github.nelayanapp.data.remote.model.Token
import javax.inject.Inject

class TokenHolder @Inject constructor(
    @ApplicationContext _context: Context
) {
    companion object {
        private const val PREF_NAME = "LDTokenHolder"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_FCM_TOKEN = "key_fcm_token"
    }

    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var privateMode = 0

    init {
        pref = _context.getSharedPreferences(PREF_NAME, privateMode)
        editor = pref.edit()
        editor.apply()
    }

    fun getToken(): Token?{
        return Gson().fromJson(pref.getString(KEY_TOKEN, null), Token::class.java)
    }

    fun storeToken (token: Token) {
        editor.putString(KEY_TOKEN, Gson().toJson(token))
        editor.commit()
    }

    fun setTokenFcm(fcmToken: String) {
        editor.putString(KEY_FCM_TOKEN, fcmToken)
        editor.commit()
    }

    fun getTokenFcm(): String? {
        return pref.getString(KEY_FCM_TOKEN, null)
    }

    fun clearToken() {
        editor.clear()
        editor.commit()
    }
}