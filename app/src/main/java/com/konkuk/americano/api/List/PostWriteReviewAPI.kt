package com.konkuk.americano.api.List

import android.util.Log
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.model.WriteReviewData
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostWriteReviewAPI {
    private const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjQsImlhdCI6MTYyMjIyNzIwNH0.mDR5xu4O2fV7yxzjm1JBM8qqHSF7ufmvv7COEp2shho"

    fun call(data: WriteReviewData, callback: RetrofitClient.callback) {
        // data json 변환
        val body = JSONObject()
        try {
            body.put("storeId", data.storeId)
            body.put("content", data.content)
            body.put("image", data.image.toString())
            body.put("flavor", data.flavor)
            body.put("sour", data.sour)
            body.put("bitter", data.bitter)
            body.put("aftertaste", data.aftertaste)
            body.put("zest", data.zest)
            body.put("balance", data.balance)
        } catch (e: JSONException) {
            callback.callbackMethod(false, "parse error")
        }

        RetrofitClient.postWriteReview().postWriteReview(
            token,
            body.toString())
            .enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                try {
                    if (response.isSuccessful){
                        callback.callbackMethod(true,response.body().toString())
                    }
                    else {
                        val jsonObject = JSONObject(response.errorBody()?.string())
                        callback.callbackMethod(false,jsonObject.getString("message"))
                    }
                    Log.i("call", call.toString())
                    Log.i("response", response.toString())
                }
                catch (e : JSONException) {
                    callback.callbackMethod(false, "ERROR")
                    Log.i("ERROR", e.printStackTrace().toString())
                }
            }
            override fun onFailure(call: Call<String?>, t: Throwable) {
                callback.callbackMethod(false, "ERROR")
                Log.i("ERROR-FAIL", t.printStackTrace().toString())
            }
        })

    }
}