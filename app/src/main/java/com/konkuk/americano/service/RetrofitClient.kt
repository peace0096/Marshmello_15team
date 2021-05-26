package com.konkuk.americano.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("http://15.165.96.180:7782/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ServiceApp::class.java)
}