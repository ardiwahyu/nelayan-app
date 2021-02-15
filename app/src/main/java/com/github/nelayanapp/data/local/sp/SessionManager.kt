package com.github.nelayanapp.data.local.sp

import android.content.Context
import android.content.SharedPreferences
import com.github.nelayanapp.data.remote.model.Profile
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(
    @ApplicationContext val _context: Context,
    private val tokenHolder: TokenHolder
) {
    companion object {

        private const val PREF_NAME = "LDLocalStorage"
        private const val IS_LOGIN = "isLoggedIn"
        private const val KEY_PROFILE = "key_login"
    }

    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var privateMode = 0

    init {
        pref = _context.getSharedPreferences(PREF_NAME, privateMode)
        editor = pref.edit()
        editor.apply()
    }

    fun getDataProfile(): Profile? {
        return Gson().fromJson(pref.getString(KEY_PROFILE, null), Profile::class.java)
    }

    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

    fun createLoginSession(profile: Profile) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_PROFILE, Gson().toJson(profile))
        editor.commit()
    }

    fun logoutUser() {
        editor.clear()
        editor.commit()
        tokenHolder.clearToken()
//        _context.startActivity(_context.intentFor<LoginActivity>().clearTask().newTask())
    }
}