package com.konkuk.americano.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.konkuk.americano.API.List.GetStoreDetailAPI
import com.konkuk.americano.API.List.UploadImageAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.StoreReviewData
import com.konkuk.americano.Repo.UserMe_Repo
import com.konkuk.americano.model.StoreDetailData
import com.konkuk.americano.repo.StoreDetailRepo
import com.konkuk.americano.repo.StoreReviewsRepo
import org.json.JSONArray
import org.json.JSONObject

class StoreDetailViewModel(val context: Context, val activity: Activity, val storeId: Int) {
    var responseOk = MutableLiveData<Int>()
    var selectedStoreId = MutableLiveData<Int>()

    var storeDetailData: MutableLiveData<StoreDetailData> = MutableLiveData()
    var storeReviews: MutableLiveData<ArrayList<StoreReviewData>> = MutableLiveData(arrayListOf())

    var images: MutableLiveData<ArrayList<Bitmap>> = MutableLiveData(arrayListOf())

    var editResponseOk = MutableLiveData<Int>()
    var getUserIdOk = MutableLiveData<Int>()

    init {
        //storeDetailLiveData.postValue(StoreDetailRepo.getInstance().getModel())
        responseOk.value = 0
        editResponseOk.value = 0
        GetStoreDetailAPI.call(storeId,
            object : RetrofitClient.callback {
                override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                    if (isSuccessful && result != null) {
                        val jsonObject = JSONObject(result)
                        val gson = Gson()
                        var tData : StoreDetailData? = null
                        tData = gson.fromJson(jsonObject.toString(), StoreDetailData::class.java)
                        StoreDetailRepo.getInstance().setModel(tData!!)
                        storeDetailData.postValue(StoreDetailRepo.getInstance().getModel())
                        selectedStoreId.value = storeId
                    } else {
                        activity.finish() // ???????
                    }
                }
            })
    }

    fun setLiveData(storeId: Int) {
        selectedStoreId.value = storeId
    }

    fun callGetStoreDetailAPI() {
        responseOk.value = 0
        StoreDetailRepo.getInstance().callGetStoreDetailAPI(
            object : RetrofitClient.callback {
                override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                    if (isSuccessful && result != null) {
                        val jsonObject = JSONObject(result)
                        val gson = Gson()
                        var tData : StoreDetailData? = null
                        tData = gson.fromJson(jsonObject.toString(), StoreDetailData::class.java)
                        StoreDetailRepo.getInstance().setModel(tData!!)
                        //storeDetailLiveData.postValue(StoreDetailRepo.getInstance().getModel())
                        storeDetailData.postValue(StoreDetailRepo.getInstance().getModel())

                        responseOk.value = responseOk.value!! + 1
                    } else {
                        activity.finish() // ???????
                    }
                }
            }
        )
    }

    fun callGetStoreReviewsAPI() {
        StoreReviewsRepo.getInstance().callGetStoreReviewsAPI(
            object : RetrofitClient.callback {
                override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                    if (isSuccessful && result != null) {
                        val jsonArray = JSONArray(result)
                        Log.i("RESULT", jsonArray.toString())
                        val gson = Gson()
                        var tDataList: ArrayList<StoreReviewData> = arrayListOf()
                        for (i in 0 until jsonArray.length()) {
                            val tData = gson.fromJson(jsonArray.get(i).toString(), StoreReviewData::class.java)
                            tDataList.add(tData)
                        }
                        StoreReviewsRepo.getInstance().setModel(tDataList)
                        storeReviews.postValue(StoreReviewsRepo.getInstance().getModel())

                        responseOk.value = responseOk.value!! + 1
                    } else {
                        activity.finish()
                    }
                }
            },
            selectedStoreId.value!!)
    }

    fun callSetImages() {
        images.value!!.clear()
        if (selectedStoreId.value != 0) {
            for (i in 0 until storeDetailData.value!!.image.size) {
                Glide.with(context)
                    .asBitmap().load(storeDetailData.value!!.image[i])
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            // viewmodel 의 array 안의 내용을 변경하는 것은 observe에 안됨
                            // 따라서 아예 일단 통째로 바꿔줬음...
                            val temp = arrayListOf<Bitmap>()
                            for (i in 0 until images.value!!.size) {
                                temp.add(images.value!![i])
                            }
                            temp.add(resource)
                            images.value = temp
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            //TODO("Not yet implemented")
                        }
                    })
            }
            responseOk.value = responseOk.value!! + 1
        }
    }

    fun callEditStoreAPI(title: String, content: String, images: ArrayList<Bitmap>) {
        var imgStringArray = ArrayList<String>()

        UploadImageAPI.call(context, images, object : RetrofitClient.callback {
            override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                // super.imageUploadCallback(isSuccessful, result)
                if (isSuccessful) {
                    for (i in 0 until result.length()) {
                        imgStringArray.add(result.get(i) as String)
                        Log.i("img url $i", (result.get(i) as String).toString())
                    }
                    val resultJsonArray = JSONArray(imgStringArray)

                    StoreDetailRepo.getInstance().callPutEditStoreAPI(title, content, resultJsonArray.toString(),
                    object : RetrofitClient.callback {
                        override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                            // super.callbackMethod(isSuccessful, result)
                            Log.i("DATA", "$title \n $content \n $isSuccessful")
                            if (isSuccessful) {
                                editResponseOk.value = 1
                            } else {
                                editResponseOk.value = -1
                            }
                        }
                    })
                } else {
                    editResponseOk.value = -1
                }
            }
        })
    }

    fun callGetUserMeAPI() {

        UserMe_Repo.getInstance().callGetUserMeAPI(object: RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                // super.callbackMethod(isSuccessful, result)
                if (isSuccessful && result != null) {
                    val jsonObject = JSONObject(result)
                    val gson = Gson()
                    val data = gson.fromJson(jsonObject.toString(), TUser::class.java)
                    Log.i("DATA", data.toString())
                    getUserIdOk.postValue(data.userId)
                } else {
                    getUserIdOk.postValue(-1)
                }
            }
        })
    }
    data class TUser(
        @SerializedName("userId")
        @Expose
        val userId: Int,
        @SerializedName("loginId")
        @Expose
        val loginId:String,
        @SerializedName("nickname")
        @Expose
        val nickname:String,
        @SerializedName("profileImage")
        @Expose
        val profileImage: ArrayList<String>,
        @SerializedName("latitude")
        @Expose
        val latitude:Double?,
        @SerializedName("longitude")
        @Expose
        val langitude:Double?
    )
}