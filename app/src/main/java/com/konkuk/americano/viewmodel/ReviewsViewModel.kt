package com.konkuk.americano.ViewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.StoreReviewData
import com.konkuk.americano.Repo.StoreReviewsRepo
import org.json.JSONArray

class ReviewsViewModel {
    var profileImage = MutableLiveData<Bitmap>()
    var storeImages = MutableLiveData<ArrayList<Bitmap>>()
    var recentReviewModel = MutableLiveData<ArrayList<StoreReviewData>>()

    init {
        storeImages.value = arrayListOf()
    }

    fun loadProfileImage(context: Context, url: String) {
        if (url != null) {
            Glide.with(context)
                .asBitmap().load(url).circleCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?) {
                        profileImage.value = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        //TODO("Not yet implemented")
                    }

                })
        }
    }

    fun loadStoreImages(context: Context, urls: ArrayList<String>) {
        storeImages.value!!.clear()
        if (urls != null && urls.size != 0) {
            for (i in 0 until urls.size) {
                Log.i("urls[i]", urls[i])
                Glide.with(context)
                    .asBitmap().load(urls[i])
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            // viewmodel 의 array 안의 내용을 변경하는 것은 observe에 안됨
                            // 따라서 아예 일단 통째로 바꿔줬음...
                            val temp = arrayListOf<Bitmap>()

                            for (i in 0 until storeImages.value!!.size) {
                                temp.add(storeImages.value!![i])
                            }
                            temp.add(resource)
                            storeImages.value = temp
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            //TODO("Not yet implemented")
                        }
                    })
            }
        } else {
            // 업로드 된 이미지 없음
        }
    }

    fun loadRecentReviews() {
        StoreReviewsRepo.getInstance().callGetRecentReview(object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val list = ArrayList<StoreReviewData>()
                        val gson = Gson()
                        val jsonArray = JSONArray(result)
                        for(i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            list.add(gson.fromJson(jsonObject.toString(), StoreReviewData::class.java))
                        }
                        StoreReviewsRepo.getInstance().setRecentReviewModel(list)
                        recentReviewModel.value = StoreReviewsRepo.getInstance().getRecentReviewModel()
                    }
                }
            }
        })
    }


}