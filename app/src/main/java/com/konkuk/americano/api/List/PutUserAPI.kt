package com.konkuk.americano.api.List

import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.repo.UserMe_Repo
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

object PutUserAPI {

    fun call( nickname : String, profileImage : ArrayList<String>, callback: RetrofitClient.callback) {
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
