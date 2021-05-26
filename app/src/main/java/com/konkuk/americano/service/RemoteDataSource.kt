package com.konkuk.americano.service

import retrofit2.Response

interface RemoteDataSource {
    fun registerId(user: User, onResponse: (Response<Token>) -> Unit, onFailure : (Throwable) -> Unit)
    fun login(user: User, onResponse: (Response<Token>) -> Unit, onFailure : (Throwable) -> Unit)
}