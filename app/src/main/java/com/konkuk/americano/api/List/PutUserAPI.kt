package com.konkuk.americano.API.List

import android.content.Context
import android.widget.Toast
import com.konkuk.americano.API.APIInterface
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Repo.UserMe_Repo
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response
import retrofit2.Retrofit

object PutUserAPI {

    fun call( nickname : String, profileImage : String, callback: RetrofitClient.callback) {
        val body = JSONObject()
        try {
            body.put("nickname", nickname)
            body.put("profileImage", profileImage)
        } catch (e: JSONException) {
            callback.callbackMethod(false, null)
        }

        RetrofitClient.getBaseClient().putUser(UserMe_Repo.getInstance().gettoken(),body.toString()).enqueue(object : Callback<String?>{
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                try {
                    if (response.isSuccessful){
                        callback.callbackMethod(true,response.body().toString())
                    }
                    else {
                        val jsonObject = JSONObject(response.errorBody()?.string())
                        callback.callbackMethod(false,jsonObject.getString("message"))

                    }
                }
                catch (e : JSONException){
                    callback.callbackMethod(false, "잠시 후 시도해주세요 ")
                }
            }
            override fun onFailure(call: Call<String?>, t: Throwable) {
                callback.callbackMethod(false, "잠시 후 시도해주세요 ")
            }
        })




    }



}
