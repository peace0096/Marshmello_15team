package com.konkuk.americano.ViewModel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.Store_Model
import com.konkuk.americano.Repo.MyStore_Repo
import org.json.JSONArray

class MyStoreViewModel(val context : Context, val activity : Activity) {

    var storeliveData : MutableLiveData<ArrayList<Store_Model>> = MutableLiveData<ArrayList<Store_Model>>()

    init {
        storeliveData.postValue(MyStore_Repo.getInstance().getmodel())
    }


    fun callGetMystoreAPI(){
        MyStore_Repo.getInstance().callMyStoreAPI(object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if (isSuccessful && result != null){
                    var tempmodel : ArrayList<Store_Model> = ArrayList<Store_Model>()
                    var jsonarr = JSONArray(result)
                    var gson = Gson()

                    for (i in 0 until jsonarr.length()){
                        tempmodel.add(gson.fromJson(jsonarr.get(i).toString(),Store_Model::class.java))
                    }
                    MyStore_Repo.getInstance().setmodel(tempmodel)
                    storeliveData.postValue(MyStore_Repo.getInstance().getmodel())
                }
                else {
                    activity.finish()
                }
            }
        })
    }





}
