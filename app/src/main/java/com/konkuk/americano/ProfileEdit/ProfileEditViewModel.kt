package com.konkuk.americano.ProfileEdit

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.UserMe_Model
import com.konkuk.americano.Repo.UserMe_Repo
import org.json.JSONObject


class ProfileEditViewModel {

    val usermemodel = MutableLiveData<UserMe_Model>()



    init {

    }


    fun changeUserProfile(nickname : String){
        var dummy = ArrayList<String>()
        dummy.add("")
        UserMe_Repo.getInstance().callUserPutAPI(nickname,dummy,object : RetrofitClient.callback{
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if (isSuccessful){
                    UserMe_Repo.getInstance().callGetUserMeAPI(object : RetrofitClient.callback{
                        override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                            if (isSuccessful && result != null){
                                var gson = Gson()
                                UserMe_Repo.getInstance().setModel(gson.fromJson(result,UserMe_Model::class.java))
                                usermemodel.value = UserMe_Repo.getInstance().getModel()
                            }
                            else {

                            }

                        }
                    })
                }
            }
        })
    }





}