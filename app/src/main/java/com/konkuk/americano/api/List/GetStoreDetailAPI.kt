package com.konkuk.americano.api.List

import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.repo.UserMe_Repo
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetStoreDetailAPI {

    fun call(callback: RetrofitClient.callback, storeId: Int) {
        RetrofitClient.getStoreDetail().getStoreDetail(
            UserMe_Repo.getInstance().gettoken(),
            storeId = storeId)
            .enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    try {
                        if (response.isSuccessful){
                            callback.callbackMethod(true, response.body().toString())
                        }
                        else {
                            val jsonObject = JSONObject(response.errorBody()?.string())
                            callback.callbackMethod(false,jsonObject.getString("message"))
                        }
                    }
                    catch (e : JSONException) {
                        callback.callbackMethod(false, "onResponse - ERROR")
                    }
                }
                override fun onFailure(call: Call<String?>, t: Throwable) {
                    callback.callbackMethod(false, "onFailure - ERROR")
                }
            })

    }
}