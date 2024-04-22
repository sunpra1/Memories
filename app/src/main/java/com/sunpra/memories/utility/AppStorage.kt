package com.sunpra.memories.utility

import android.content.Context
import android.content.SharedPreferences

class AppStorage(context: Context) {
    private val tokenKey : String = "token"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("preference_storage", Context.MODE_PRIVATE)

    var token: String? = null
        set(value){
            sharedPreferences.edit().putString(tokenKey, value).apply()
            field = value
        }
        get() = sharedPreferences.getString(tokenKey, null)
}