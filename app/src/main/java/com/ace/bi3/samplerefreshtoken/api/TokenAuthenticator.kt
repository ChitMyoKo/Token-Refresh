package com.ace.bi3.samplerefreshtoken.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ace.bi3.samplerefreshtoken.shared_preference.MyPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenAuthenticator(
    private val context: Context,
    private val myServiceHolder: MyServiceHolder?
) : Authenticator {
    private var preferences = MyPreference.getInstance(context)
    private lateinit var newToken: String
    private var value: Any? = null
    private var pref = MyPreference.getInstance(context)
    override fun authenticate(route: Route, response: Response): Request? {
        if (myServiceHolder == null) {
            return null
        }

        val refreshToken = preferences.getToken()

        val retrofitResponse = myServiceHolder.get()!!.refreshToken(refreshToken!!)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newToken = it.token
                value = response.request().newBuilder()
                    .header("Authorization", newToken)
                    .build()
                pref.saveToken(it.token)
                Log.d("suc",it.token)
            }, {
                newToken = it.localizedMessage!!
                value = null
            })

        return value as Request
    }
}

