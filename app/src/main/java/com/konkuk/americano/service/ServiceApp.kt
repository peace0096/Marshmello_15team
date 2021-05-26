package com.konkuk.americano.service

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceApp {
    @POST("user/register")
    fun registerId(@Body user:User) : Call<Token>

    @POST("user/login")
    fun login(@Body user:User) : Call<Token>
}