package com.konkuk.americano.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.konkuk.americano.API.List.UploadImageAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.api.List.PostWriteReviewAPI
import com.konkuk.americano.model.WriteReviewData
import org.json.JSONArray

class WriteReviewViewModel(val context: Context, val activity: Activity) {

    var responseInt: MutableLiveData<Int> = MutableLiveData() // 응답 코드 1이 올라오면 성공

    fun callPostWriteReviewAPI(data: WriteReviewData, images: ArrayList<Bitmap>) {
        var imgStringArray = ArrayList<String>()

        UploadImageAPI.call(context, images, object : RetrofitClient.callback {
            override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                // super.imageUploadCallback(isSuccessful, result) -> empty
                if (isSuccessful) {
                    for (i in 0 until result.length() ) {
                        imgStringArray.add(result.get(i) as String)
                        Log.i("img url $i", (result.get(i) as String).toString())
                    }
                    for (i in 0 until imgStringArray.size) {
                        data.image.add(imgStringArray[i])
                    }

                    // image 부분 jsonarray 변경하는 것은 아래 함수에서 처리
                    PostWriteReviewAPI.call(data, object : RetrofitClient.callback {
                        override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                            Log.i("DATA", data.toString())
                            if (isSuccessful && result != null) {
                                if (result == "") {
                                    responseInt.postValue(1)
                                } else {
                                    responseInt.postValue(0)
                                }
                            } else {
                                responseInt.postValue(0)
                            }
                        }

                    })
                } else {
                    responseInt.postValue(0)
                    Log.i("ERROR - imageupload", responseInt.value.toString())
                }
            }
        })
    }

}