package com.konkuk.americano.Repo

import com.konkuk.americano.API.List.GetStoreDetailAPI
import com.konkuk.americano.API.List.PutMyStoreAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.StoreDetailData

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

    fun callGetStoreDetailAPI(callback: RetrofitClient.callback) {
        GetStoreDetailAPI.call(model!!.storeId, callback)
    }

    fun callPutEditStoreAPI(title: String, content: String, images: String, callback: RetrofitClient.callback) {
        PutMyStoreAPI.call(model!!.storeId, title, content, images, callback)
    }
}