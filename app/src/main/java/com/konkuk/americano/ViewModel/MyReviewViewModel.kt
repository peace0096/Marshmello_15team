package com.konkuk.americano.ViewModel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.Review_Model
import com.konkuk.americano.Model.Store_Model
import com.konkuk.americano.Repo.MyReview_Repo
import com.konkuk.americano.Repo.MyStore_Repo
import org.json.JSONArray

class MyReviewViewModel(val context : Context, val activity : Activity) {

    var reviewliveData : MutableLiveData<ArrayList<Review_Model>> = MutableLiveData<ArrayList<Review_Model>>()

    init {
        reviewliveData.postValue(MyReview_Repo.getInstance().getmodel())
    }


    fun callGetMyReviewAPI(){

        MyReview_Repo.getInstance().callMyReviewAPI(object : RetrofitClient.callback{
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if (isSuccessful && result != null){
                    var tempmodel : ArrayList<Review_Model> = ArrayList<Review_Model>()
                    var jsonarr = JSONArray(result)
                    var gson = Gson()

                    for (i in 0 until jsonarr.length()){
                        tempmodel.add(gson.fromJson(jsonarr.get(i).toString(),Review_Model::class.java))
                    }
                    MyReview_Repo.getInstance().setmodel(tempmodel)
                    reviewliveData.postValue(MyReview_Repo.getInstance().getmodel())
                }
                else {
                    activity.finish()
                }
            }
        })

    }




}