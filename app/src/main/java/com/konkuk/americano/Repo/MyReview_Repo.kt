package com.konkuk.americano.Repo

import com.konkuk.americano.API.List.GetMyReviewAPI
import com.konkuk.americano.API.List.GetMyStoreAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.Review_Model
import com.konkuk.americano.Model.Store_Model

class MyReview_Repo {

    companion object {
        @Volatile
        private var instance: MyReview_Repo? = null

        @JvmStatic
        fun getInstance(): MyReview_Repo =
            instance ?: synchronized(this) {
                instance ?: MyReview_Repo().also {
                    instance = it
                }
            }
    }

    private var model : ArrayList<Review_Model> = ArrayList<Review_Model>()

    fun setmodel(input : ArrayList<Review_Model>){
        model = input
    }
    fun getmodel() : ArrayList<Review_Model> {
        return model
    }

    fun callMyReviewAPI(callback : RetrofitClient.callback) {
        GetMyReviewAPI.call(callback)
    }

}