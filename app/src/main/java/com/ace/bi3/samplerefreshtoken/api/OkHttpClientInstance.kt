package com.ace.bi3.samplerefreshtoken.api

import android.content.Context
import com.ace.bi3.samplerefreshtoken.shared_preference.MyPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit


class OkHttpClientInstance {
    inner class Builder(
        private val context: Context?,
        private val myServiceHolder: MyServiceHolder
    ) {
        private val headers = HashMap<String, String>()
        private var pref = MyPreference.getInstance(context!!)
        fun addHeader(key: String, value: String): Builder {
            headers.put(key, value)
            return this
        }

        fun build(): OkHttpClient {
            val authenticator = TokenAuthenticator(context!!, myServiceHolder)

            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(
                    object : Interceptor {
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val requestBuilder = chain.request().newBuilder()
                                .addHeader("accept", "*/*")
                                .addHeader("accept-encoding:gzip", "gzip, deflate")
                                .addHeader("accept-language", "en-US,en;q=0.9")

                            // Add additional headers
                            //val it = headers.entrySet().iterator()

                            if (headers.isNotEmpty()) {
                                for (entry in headers.entries) {
                                    requestBuilder.addHeader(entry.key, entry.value)
                                }
                            }
                            val token = pref.getToken()

                            if (token != null) {
                                requestBuilder.addHeader("Authorization", token)
                            }
                            return chain.proceed(requestBuilder.build())
                        }
                    }
                )
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

            okHttpClientBuilder.authenticator(authenticator)

            return okHttpClientBuilder.build()
        }
    }
}