package com.konkuk.americano.repo

import com.konkuk.americano.api.List.GetStoreDetailAPI
import com.konkuk.americano.api.RetrofitClient
import com.konkuk.americano.model.StoreDetailData

class StoreDetailRepo {
    companion object {
        @Volatile
        private var instance: StoreDetailRepo? = null

        @JvmStatic
        fun getInstance(): StoreDetailRepo =
            instance?: synchronized(this) {
                instance ?: StoreDetailRepo().also {
                    instance = it
                }
            }
    }

    private var model: StoreDetailData? = null

    fun setModel(input: StoreDetailData) {
        model = input
    }
    fun getModel(): StoreDetailData? {
        return model?: null
    }

    fun callGetStoreDetailAPI(callback: RetrofitClient.callback, storeId: Int) {
        GetStoreDetailAPI.call(callback, storeId)
    }
}