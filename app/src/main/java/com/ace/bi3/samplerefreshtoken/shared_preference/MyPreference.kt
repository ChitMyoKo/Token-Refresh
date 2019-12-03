package com.ace.bi3.samplerefreshtoken.shared_preference

import android.content.Context

class MyPreference(context: Context) {
    private val myPreference =
        context.getSharedPreferences("preference.name", Context.MODE_PRIVATE)

    companion object {

        private var instance: MyPreference? = null
        fun getInstance(context: Context): MyPreference {
            if (instance == null) {
                instance = MyPreference(context)
            }
            return instance as MyPreference
        }
    }
    fun saveUserName(userName: String) {
        with(myPreference.edit())
        {
            putString("USER_NAME", userName)
            apply()
        }

    }

    fun getUserName(): String? = myPreference.getString("USER_NAME", null)

    fun saveToken(userName: String) {
        with(myPreference.edit())
        {
            putString("TOKEN", userName)
            apply()
        }

    }

    fun getToken(): String? = myPreference.getString("TOKEN", null)
}