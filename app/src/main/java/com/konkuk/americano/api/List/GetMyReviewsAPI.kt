package com.konkuk.americano.api.List

import android.util.Log
import com.konkuk.americano.api.APIInterface
import com.konkuk.americano.api.RetrofitClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetMyReviewsAPI {
    private const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTYyMjIyNzIwNH0.mDR5xu4O2fV7yxzjm1JBM8qqHSF7ufmvv7COEp2shho"

    fun call(callback: RetrofitClient.callback) {
        RetrofitClient.getBaseReview().getMyReviews(token)
            .enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    try {
                        if (response.isSuccessful) {
                            callback.callbackMethod(true, response.body().toString())
                        } else {
                            val jsonObject = JSONObject(response.errorBody()?.string())
                            callback.callbackMethod(false,jsonObject.getString("message"))
                        }
                        Log.i("call", call.toString())
                        Log.i("response", response.toString())
                    } catch (e: JSONException) {
                        callback.callbackMethod(false, "ERROR")
                        Log.i("ERROR", e.printStackTrace().toString())
                    }

                }
                override fun onFailure(call: Call<String?>, t: Throwable) {
                    callback.callbackMethod(false, "ERROR")
                    Log.i("ERROR", t.printStackTrace().toString())
                }

            })
    }
}