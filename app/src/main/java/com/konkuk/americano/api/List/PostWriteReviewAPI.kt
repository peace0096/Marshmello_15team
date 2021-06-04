package com.konkuk.americano.api.List

import android.util.Log
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.model.WriteReviewData
import com.konkuk.americano.repo.UserMe_Repo
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostWriteReviewAPI {
    fun call(data: WriteReviewData, callback: RetrofitClient.callback) {
        // 인자로 받은 data.image는 arraylist 형식임. 이를 반드시 JSONArray의 형태로 toString() 해서 넣을 것
        // jsonarray 형태: "["url", "url"]" : OK (올바른 형식)
        // jsonarray로 바꾸지 않고 arraylist를 toString 하면: "[url, url]" : NO! 일단은 응답으로 ok(200)이 나오지만 image 데이터를 서버에서 가져와서 보내주려고 할 때 에러 나는 것 같음

        val jsonArrayImageUrls = JSONArray(data.image)

        val body = JSONObject()
        try {
            body.put("storeId", data.storeId)
            body.put("content", data.content)
            body.put("image", jsonArrayImageUrls.toString())
            body.put("flavor", data.flavor)
            body.put("sour", data.sour)
            body.put("bitter", data.bitter)
            body.put("aftertaste", data.aftertaste)
            body.put("zest", data.zest)
            body.put("balance", data.balance)
        } catch (e: JSONException) {
            callback.callbackMethod(false, "parse error")
        }
        Log.i("BODY", body.toString())

        RetrofitClient.postWriteReview().postWriteReview(
            UserMe_Repo.getInstance().gettoken(),
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