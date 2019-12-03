package com.ace.bi3.samplerefreshtoken

import android.telecom.Call
import com.ace.bi3.samplerefreshtoken.data_class.FilterData
import com.ace.bi3.samplerefreshtoken.network_response.Boat
import com.ace.bi3.samplerefreshtoken.network_response.Token
import io.reactivex.Observable
import retrofit2.http.*


interface MyService {
    @POST("api/boatTrip/retrieveBoatforToday")
    fun getTodayDBL(
        @Body filterData: FilterData,
        @Header("Authorization") token: String,
        @Header("UserName") userName: String
    ): Observable<List<Boat>>

    @POST("api/auth/refresh")
    fun refreshToken(@Header("Authorization") token: String): Observable<Token>
}