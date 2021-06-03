package com.konkuk.americano.API.List

import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Repo.UserMe_Repo
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PutMyStoreAPI {
    fun call(storeId : Int,  title : String,content : String, image : String, callback: RetrofitClient.callback) {
        val body = JSONObject()
        try {
            body.put("title", title)
            body.put("content", content)
            body.put("image",image)
            body.put("storeId",storeId)
        } catch (e: JSONException) {
            callback.callbackMethod(false, null)
        }

        RetrofitClient.getBaseClient().putmystore(UserMe_Repo.getInstance().gettoken(),body.toString()).enqueue(object :
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