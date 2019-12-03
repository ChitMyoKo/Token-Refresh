package com.ace.bi3.samplerefreshtoken.api

import androidx.annotation.Nullable
import com.ace.bi3.samplerefreshtoken.MyService


class MyServiceHolder {
    internal var myService: MyService? = null

    @Nullable
    fun get(): MyService? {
        return myService
    }

    fun set(myService: MyService) {
        this.myService = myService
    }
}