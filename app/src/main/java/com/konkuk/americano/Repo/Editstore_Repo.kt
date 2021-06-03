package com.konkuk.americano.Repo

import com.konkuk.americano.API.List.GetStoreDetailAPI
import com.konkuk.americano.API.List.PutMyStoreAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.Store_Model

class Editstore_Repo {
    companion object {
        @Volatile
        private var instance: Editstore_Repo? = null

        @JvmStatic
        fun getInstance(): Editstore_Repo =
            instance ?: synchronized(this) {
                instance ?: Editstore_Repo().also {
                    instance = it
                }
            }
    }

    private lateinit var model : Store_Model

    private var currentStoreId : Int = 0

    fun setmodel(input : Store_Model){
        model = input
    }
    fun getmodel() : Store_Model {
        return model
    }

    fun setStoreId(storeId : Int){
        currentStoreId = storeId
    }





    fun callGetStoreDetailAPI(callback: RetrofitClient.callback){
        GetStoreDetailAPI.call(currentStoreId,callback)
    }

    fun callPutStoreAPI(title : String, content : String, image : String,callback: RetrofitClient.callback){
        PutMyStoreAPI.call(currentStoreId,title,content,image,callback)
    }





}