package com.konkuk.americano.api.List

import android.util.Log
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Repo.UserMe_Repo
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetStoreReviews {
    fun call(callback: RetrofitClient.callback, storeId: Int) {
        RetrofitClient.getBaseClient().getStoreReviews(
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
                        Log.i("response", response.toString())
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