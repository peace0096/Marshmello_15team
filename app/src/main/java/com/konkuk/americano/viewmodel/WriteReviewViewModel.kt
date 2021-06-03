package com.konkuk.americano.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.konkuk.americano.api.List.PostWriteReviewAPI
import com.konkuk.americano.api.List.UploadImageAPI
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.model.WriteReviewData
import org.json.JSONArray

class WriteReviewViewModel(val context: Context, val activity: Activity) {
    data class ImageModel(var bitmap: Bitmap?, var stringSrc: String?)

    var responseInt: MutableLiveData<Int> = MutableLiveData() // 응답 코드



    var image1 = MutableLiveData<ImageModel>()
    var image2 = MutableLiveData<ImageModel>()
    var image3 = MutableLiveData<ImageModel>()
    init {
        image1.value = ImageModel(null, null)
        image2.value = ImageModel(null, null)
        image3.value = ImageModel(null, null)
    }

    // data(image 빈 array) +
    fun callPostWriteReviewAPI2(data: WriteReviewData) {
        PostWriteReviewAPI.call(
            data,
            object : RetrofitClient.callback {
                override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                    if (isSuccessful && result != null) {
                        Log.i("result - write review", result.toString())
                        if (result == "") {
                            responseInt.postValue(1)
                        } else {
                            Log.i("??? repsonse - write review", result.toString())
                            responseInt.postValue(0)
                        }
                    } else {
                        responseInt.postValue(0)
                    }
                }
            })
    }

    fun callPostWriteReviewAPI(data: WriteReviewData, images: ArrayList<Bitmap>) {
        //val imgArr = arrayOf(image1, image2, image3)

        //var bitArray = ArrayList<Bitmap>()
        var imgStringArray = ArrayList<String>()

//        for (it in imgArr) {
//            if (it.value!!.bitmap != null) {
//                bitArray.add(it.value!!.bitmap!!)
//            } else if (it.value!!.stringSrc != null) {
//                imgStringArray.add(it.value!!.stringSrc!!)
//            }
//        }
        UploadImageAPI.call(context, images, object : RetrofitClient.callback {
            override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                // super.imageUploadCallback(isSuccessful, result) -> empty
                if (isSuccessful) {
                    for (i in 0 until result.length() ) {
                        imgStringArray.add(result.get(i) as String)
                        Log.i("img str", (result.get(i) as String).toString())
                    }
                    for (i in 0 until imgStringArray.size) {
                        data.image.add(imgStringArray[i])
                    }

                    PostWriteReviewAPI.call(data, object : RetrofitClient.callback {
                        override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                            Log.i("DATA", data.toString())
                            if (isSuccessful && result != null) {
                                Log.i("result - write review", result.toString())
                                if (result == "") {
                                    responseInt.postValue(1)
                                } else {
                                    Log.i("??? repsonse - write review", result.toString())
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