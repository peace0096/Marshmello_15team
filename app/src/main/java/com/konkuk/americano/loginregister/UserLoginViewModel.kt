package com.konkuk.americano.loginregister

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.repo.UserMe_Repo
import org.json.JSONObject

class UserLoginViewModel {
    val tokenmodel = MutableLiveData<String>()

    fun login(loginId:String, password:String) {
        UserMe_Repo.getInstance().callPostUserLogin(loginId, password, object: RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val gson = Gson()
                        val jsonObject = JSONObject(result)
                        UserMe_Repo.getInstance().setToken(jsonObject.get("token").toString())
                        tokenmodel.value = jsonObject.get("token").toString()
                    }
                }
            }
        })
    }

    fun register(loginId:String, password:String, nickname:String, profileImage:String, latitude:Double, longitude:Double) {
        UserMe_Repo.getInstance().callPostUserRegister(loginId, password, nickname, profileImage, latitude, longitude, object :RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val gson = Gson()
                        val jsonObject = JSONObject(result)
                        UserMe_Repo.getInstance().setToken(jsonObject.get("token").toString())
                        tokenmodel.value = jsonObject.get("token").toString()
                    }
                }
            }


        })
    }

}