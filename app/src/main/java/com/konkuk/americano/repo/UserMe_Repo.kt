package com.konkuk.americano.repo

import com.konkuk.americano.api.List.GETUserMeAPI
import com.konkuk.americano.api.List.POSTUserLogin
import com.konkuk.americano.api.List.POSTUserRegister
import com.konkuk.americano.api.List.PutUserAPI
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.model.UserMe_Model

class UserMe_Repo {

    companion object {
        @Volatile
        private var instance: UserMe_Repo? = null

        @JvmStatic
        fun getInstance(): UserMe_Repo =
            instance ?: synchronized(this) {
                instance ?: UserMe_Repo().also {
                    instance = it
                }
            }
    }

    private lateinit var model : UserMe_Model

    //private var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsImlhdCI6MTYyMjAzMDQwOH0.MEQTtQSf9NbwxoWRi6rj6EmmcGrZma7SdOpePiSH434"
    private var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTYyMjIyNzIwNH0.mDR5xu4O2fV7yxzjm1JBM8qqHSF7ufmvv7COEp2shho"


    fun setModel(model : UserMe_Model){
        this.model = model
    }
    fun getModel() : UserMe_Model{
        return this.model
    }

    fun setToken(token : String){
        this.token = token
    }

    fun gettoken() : String {
        return this.token
    }

    fun callUserPutAPI(nickname : String , profileImage : ArrayList<String>,callback : RetrofitClient.callback) {
        PutUserAPI.call(nickname,profileImage,callback)
    }


    fun callGetUserMeAPI(callback : RetrofitClient.callback){
        GETUserMeAPI.call(callback)
    }

    fun callPostUserLogin(loginId : String, password : String, callback : RetrofitClient.callback) {
        POSTUserLogin.call(loginId, password, callback)
    }

    fun callPostUserRegister(loginId:String, password:String, nickname:String, profileImage:String, latitude:Double, longitude:Double, callback: RetrofitClient.callback) {
        POSTUserRegister.call(loginId, password, nickname, profileImage, latitude, longitude, callback)
    }
}