package com.konkuk.americano.service

import retrofit2.Response

class UserRepository {
    val remoteDataSourceImp:RemoteDataSource = RemoteDataSourceImp()
    fun registerId(user:User, onResponse: (Response<Token>) -> Unit, onFailure: (Throwable) -> Unit) {
        remoteDataSourceImp.registerId(user, onResponse, onFailure)
    }

    fun login(user:User, onResponse: (Response<Token>) -> Unit, onFailure: (Throwable) -> Unit) {
        remoteDataSourceImp.login(user, onResponse, onFailure)
    }

}