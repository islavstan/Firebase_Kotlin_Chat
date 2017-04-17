package com.islavstan.firebasekotlinchat.utils

import android.content.Context
import android.content.SharedPreferences


class SharedPrefUtil(context:Context) {
    val mContext = context
    lateinit var mSharedPreferences:SharedPreferences
    var mEditor: SharedPreferences.Editor? = null


     fun saveString(key:String, value:String?) {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.putString(key, value)
        mEditor?.commit()

    }

    fun getString(key:String): String {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences.getString(key, null)

    }
}