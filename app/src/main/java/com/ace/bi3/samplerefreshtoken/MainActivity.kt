package com.ace.bi3.samplerefreshtoken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ace.bi3.samplerefreshtoken.api.MyServiceHolder
import com.ace.bi3.samplerefreshtoken.api.OkHttpClientInstance
import com.ace.bi3.samplerefreshtoken.data_class.FilterData
import com.ace.bi3.samplerefreshtoken.shared_preference.MyPreference
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU3NDg1MzYxMiwiaWF0IjoxNTc0ODUzMDEyfQ.xAPJmS4-awxvcP5FDHZ6v5oE2vFDFrpwA60UdLmCSUeS6FKMBj_V7HkPM7PB-hRSSCFCECZn96XnGPj5lFRfqw"
        var pref = MyPreference.getInstance(this)
        pref.saveToken(token)
        val myServiceHolder = MyServiceHolder()

        val okHttpClient = OkHttpClientInstance().Builder(this, myServiceHolder)
            .addHeader("Authorization", pref.getToken()!!)
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val myService = retrofit2.Retrofit.Builder()
            .baseUrl("http://10.3.1.101:9090/fbcts/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MyService::class.java)

        myServiceHolder.set(myService)

         btnOK.setOnClickListener {
             myService.getTodayDBL(FilterData("DEPARTURE ", "SCHEDULE", "4"), pref.getToken()!!, "ins")
                 .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe({
                     Toast.makeText(this,it.size.toString(),Toast.LENGTH_SHORT).show()
                 }, {
                     Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
                     Log.d("err",it.localizedMessage)
                 })
         }
    }
}
