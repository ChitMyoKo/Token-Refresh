package com.ace.bi3.samplerefreshtoken.network_response

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String
)