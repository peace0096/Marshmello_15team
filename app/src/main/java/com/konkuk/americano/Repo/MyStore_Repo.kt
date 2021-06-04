package com.konkuk.americano.Repo

import androidx.lifecycle.MutableLiveData
import com.konkuk.americano.API.List.GetMyStoreAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.Store_Model
import com.konkuk.americano.Model.UserMe_Model
import com.konkuk.americano.Model.StoreReviewData

class MyStore_Repo {
    companion object {
        @Volatile
        private var instance: MyStore_Repo? = null

        @JvmStatic
        fun getInstance(): MyStore_Repo =
            instance ?: synchronized(this) {
                instance ?: MyStore_Repo().also {
                    instance = it
                }
            }
    }

    private var model : ArrayList<Store_Model> = ArrayList<Store_Model>()


    fun setmodel(input : ArrayList<Store_Model>){
        model = input
    }
    fun getmodel() : ArrayList<Store_Model> {
        return model
    }

    fun callMyStoreAPI(callback : RetrofitClient.callback) {
        GetMyStoreAPI.call(callback)
    }






}