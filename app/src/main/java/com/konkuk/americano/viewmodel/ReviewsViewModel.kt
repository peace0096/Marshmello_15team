package com.konkuk.americano.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ReviewsViewModel {
    var profileImage = MutableLiveData<Bitmap>()
    var storeImages = MutableLiveData<ArrayList<Bitmap>>()

    fun loadProfileImage(context: Context, url: String) {
        if (url != null) {
            Glide.with(context)
                .asBitmap().load(url)
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
        if (urls != null && urls.size != 0) {
            for (i in 0 until urls.size) {
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
}