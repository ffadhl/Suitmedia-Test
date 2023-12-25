package com.fadhlalhafizh.suitmedia.data.preferences

import android.annotation.SuppressLint
import android.content.Context

class UserPreferences private constructor(private val mCtx: Context) {

    fun userSave(user: String) {
        val prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("name", user)
        editor.apply()
    }

    fun userClear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_preff"

        @SuppressLint("StaticFieldLeak")
        private var mInstance: UserPreferences? = null

        @Synchronized
        fun getInstance(mCtx: Context): UserPreferences {
            if (mInstance == null) {
                mInstance = UserPreferences(mCtx)
            }
            return mInstance as UserPreferences
        }
    }

}