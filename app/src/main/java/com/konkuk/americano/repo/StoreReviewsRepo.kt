package com.konkuk.americano.repo

import com.konkuk.americano.api.List.GetStoreReviews
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.model.StoreReviewData

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

    private var model: ArrayList<StoreReviewData> = arrayListOf()

    fun setModel(input: ArrayList<StoreReviewData>) {
        model = input
    }
    fun getModel(): ArrayList<StoreReviewData> {
        return model
    }

    fun callGetStoreReviewsAPI(callback: RetrofitClient.callback, storeId: Int) {
        GetStoreReviews.call(callback, storeId)
    }
}