package com.konkuk.americano.Repo

import com.konkuk.americano.API.List.GETUserMeAPI
import com.konkuk.americano.API.List.POSTUserLogin
import com.konkuk.americano.API.List.POSTUserRegister
import com.konkuk.americano.API.List.PutUserAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.UserMe_Model
import javax.security.auth.callback.Callback

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

    private var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsImlhdCI6MTYyMjAzMDQwOH0.MEQTtQSf9NbwxoWRi6rj6EmmcGrZma7SdOpePiSH434"


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