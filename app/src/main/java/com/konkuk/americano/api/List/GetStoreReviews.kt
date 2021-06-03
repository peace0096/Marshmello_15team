package com.konkuk.americano.api.List

import android.util.Log
import com.konkuk.americano.api.RetrofitClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetStoreReviews {
    private const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTYyMjIyNzIwNH0.mDR5xu4O2fV7yxzjm1JBM8qqHSF7ufmvv7COEp2shho"

    fun call(callback: RetrofitClient.callback, storeId: Int) {
        RetrofitClient.getStoreReviews().getStoreReviews(
            token,
            storeId = storeId)
            .enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    try {
                        if (response.isSuccessful){
                            Log.i("response-body", response.body().toString())
                            callback.callbackMethod(true, response.body().toString())
                        }
                        else {
                            val jsonObject = JSONObject(response.errorBody()?.string())
                            Log.i("jsonObject", jsonObject.toString())
                            callback.callbackMethod(false,jsonObject.getString("message"))
                        }
                        Log.i("response", response.toString())
                    }
                    catch (e : JSONException) {
                        callback.callbackMethod(false, "ERROR")
                        Log.e("ERROR", e.printStackTrace().toString())
                    }
                }
                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.i("ERROR", t.printStackTrace().toString())
                    callback.callbackMethod(false, "onFailure")
                }
            })

    }
}