package com.konkuk.americano.ViewModel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.konkuk.americano.API.List.UploadImageAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.Store_Model
import com.konkuk.americano.Model.UserMe_Model
import com.konkuk.americano.Repo.UserMe_Repo
import org.json.JSONArray
import org.json.JSONObject

class UserViewModel(val context: Context) {
    val tokenmodel = MutableLiveData<String>()
    val listStoreModel = MutableLiveData<ArrayList<Store_Model>>()
    val usermodel = MutableLiveData<UserMe_Model>()

    fun login(loginId:String, password:String) {
        UserMe_Repo.getInstance().callPostUserLogin(loginId, password, object:RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val gson = Gson()
                        val jsonObject = JSONObject(result)
                        UserMe_Repo.getInstance().setToken(jsonObject.get("token").toString())
                        tokenmodel.value = jsonObject.get("token").toString()
                    }
                }
                else {
                    Toast.makeText(context, "로그인 실패! 다시 입력해주세요..", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun registerImage(loginId:String, password:String, nickname:String, profileImage:Bitmap?, latitude:Double, longitude:Double) {
        val bitmaps = ArrayList<Bitmap>()
        Log.d("register", "start")
        var profileImageUrl:String = ""
        if (profileImage != null) {
            bitmaps.add(profileImage)
            UploadImageAPI.call(context, bitmaps, object : RetrofitClient.callback {
                override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                    if(isSuccessful) {
                        Log.d("register", "success")
                        val jsonObject = result.get(0)
                        profileImageUrl = jsonObject.toString()
                        Log.d("image", profileImageUrl.toString())
                        register(loginId, password, nickname, result.toString(), latitude, longitude)
                    }
                    else {
                        Log.d("image", "fail")
                    }
                }
            })

        } else {

            register(loginId, password, nickname, "[]", latitude, longitude)

        }



    }

    fun register(loginId:String, password:String, nickname:String, profileImageUrl:String, latitude:Double, longitude:Double) {
        UserMe_Repo.getInstance().callPostUserRegister(loginId, password, nickname, profileImageUrl, latitude, longitude, object :RetrofitClient.callback {
            override fun callbackMethod(isSuccessful1: Boolean, result1: String?) {
                if(isSuccessful1) {
                    if(result1 != null) {
                        val gson = Gson()
                        val jsonObject = JSONObject(result1)
                        UserMe_Repo.getInstance().setToken(jsonObject.get("token").toString())
                        tokenmodel.value = jsonObject.get("token").toString()
                    }
                } else {
                    Log.d("err", result1.toString())
                }
            }


        })
    }


    fun getStoreListLoc(latitude: Double, longitude: Double) {
        UserMe_Repo.getInstance().callGetStoreLoc(latitude, longitude, object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val list = ArrayList<Store_Model>()
                        val gson = Gson()
                        val jsonArray = JSONArray(result)
                        for(i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            list.add(gson.fromJson(jsonObject.toString(), Store_Model::class.java))
                            //Log.d("store", list[list.size - 1].title)
                        }
                        UserMe_Repo.getInstance().setStoreModel(list)
                        listStoreModel.value = UserMe_Repo.getInstance().getStoreModel()
                    }
                }
            }
        })
    }

    fun updateUserLocation(latitude: Double, longitude: Double) {
        UserMe_Repo.getInstance().callPutUserLocation(latitude, longitude, object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val jsonObject = JSONObject(result)
                        UserMe_Repo.getInstance().setToken(jsonObject.get("token").toString())
                        tokenmodel.value = jsonObject.get("token").toString()
                    }
                }
            }
        })
    }

    fun getUserMe() {
        UserMe_Repo.getInstance().callGetUserMeAPI(object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if(isSuccessful) {
                    if(result != null) {
                        val gson = Gson()
                        val jsonObject = JSONObject(result)
                        UserMe_Repo.getInstance().setModel(gson.fromJson(jsonObject.toString(), UserMe_Model::class.java))
                        usermodel.value = UserMe_Repo.getInstance().getModel()
                    }
                }
            }
        })
    }



}
