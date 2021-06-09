package com.konkuk.americano.Repo

import com.konkuk.americano.API.List.GetRecentReview
import com.konkuk.americano.API.List.GetStoreReviews
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.StoreReviewData


class StoreReviewsRepo {
    companion object {
        @Volatile
        private var instance: StoreReviewsRepo? = null

        @JvmStatic
        fun getInstance(): StoreReviewsRepo =
            instance?: synchronized(this) {
                instance ?: StoreReviewsRepo().also {
                    instance = it
                }
            }
    }
    private var recentReviewModel = ArrayList<StoreReviewData>()
    private var model: ArrayList<StoreReviewData> = arrayListOf()

    fun setModel(input: ArrayList<StoreReviewData>) {
        model = input
    }
    fun getModel(): ArrayList<StoreReviewData> {
        return model
    }

    fun getRecentReviewModel() : ArrayList<StoreReviewData>? {
        return recentReviewModel
    }

    fun setRecentReviewModel(model : ArrayList<StoreReviewData>) {
        this.recentReviewModel = model
    }


    fun callGetStoreReviewsAPI(callback: RetrofitClient.callback, storeId: Int) {
        GetStoreReviews.call(callback, storeId)
    }

    fun callGetRecentReview(callback: RetrofitClient.callback) {
        GetRecentReview.call(callback)
    }
}