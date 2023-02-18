package com.dapadz.ltechtest.data.shared_prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class SharedPrefUtils(context:Context) {

    private val sharedName = "AppConfig"
    private val prefs = context.getSharedPreferences(sharedName, MODE_PRIVATE)

    fun saveUser(phone:String, password:String) {
        prefs.edit {
            putString(SharedPrefsKey.PHONE.key, phone)
            putString(SharedPrefsKey.PASSWORD.key, password)
        }
    }

    fun getUser() : Pair<String, String> {
        val phone = prefs.getString(SharedPrefsKey.PHONE.key, null) ?:""
        val password = prefs.getString(SharedPrefsKey.PASSWORD.key, null) ?:""
        return Pair(phone, password)
    }
}