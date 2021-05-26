package com.konkuk.americano.service

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSourceImp : RemoteDataSource {
    override fun registerId(
        user: User,
        onResponse: (Response<Token>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        RetrofitClient.service.registerId(user).enqueue(object: Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                onResponse(response)
            }


        })


    }

    override fun login(
        user: User,
        onResponse: (Response<Token>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        RetrofitClient.service.login(user).enqueue(object: Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                onResponse(response)
            }


        })
    }

}