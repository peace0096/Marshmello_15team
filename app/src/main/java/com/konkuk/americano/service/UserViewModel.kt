package com.konkuk.americano.service


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

object UserViewModel : ViewModel() {
    val userRepository = UserRepository()
    val registerLiveData = MutableLiveData<Token>()
    val loginLiveData = MutableLiveData<Token>()

    init {
        registerLiveData.value = null
        loginLiveData.value = null
    }

    fun registerId(user:User) {
        userRepository.registerId(user = user,
            onResponse = {
                val token = it.body()    //String token
                registerLiveData.value = token
                Log.i("registerId : onResp``onse<token>", "${token}")
            },

            onFailure = {
                Log.i("registerId : onFailure<token>", it.message.toString())
            })
    }

    fun login(user: User) {
        userRepository.login(user = user,
            onResponse = {
                val token = it.body()    //String token
                loginLiveData.value = token
                if (token != null) {
                    Log.i("login : onResponse<token>", "${token.token.toString()}")
                }
            },

            onFailure = {
                Log.i("login : onFailure<token>", it.message.toString())
            })
    }


}