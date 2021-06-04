package com.konkuk.americano.api.List

import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.repo.UserMe_Repo
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GETUserMeAPI {

    fun call(callback: RetrofitClient.callback) {
        RetrofitClient.getBaseClient().getuserme(UserMe_Repo.getInstance().gettoken()).enqueue(object :
            Callback<String?> {
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