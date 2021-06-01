package com.konkuk.americano.API.List

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Repo.UserMe_Repo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random


object UploadImageAPI {

    fun call(context : Context, image : ArrayList<Bitmap>, callback: RetrofitClient.callback) {

        if (image.size == 0){
            callback.imageUploadCallback(true,JSONArray())
            return
        }

        var filedata = ArrayList<MultipartBody.Part>()


       for (bp in image){
           val rand = Random
           val name = System.currentTimeMillis().toString() + rand.nextInt(10000).toString() + ".png"
           var photo = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path,name)
           var output = FileOutputStream(photo)
           bp.compress(Bitmap.CompressFormat.JPEG,100,output)

           filedata.add(MultipartBody.Part.createFormData("file",name,RequestBody.Companion.create(
               "image/png".toMediaTypeOrNull(),photo)))
       }

        RetrofitClient.getBaseClient().uploadPhoto(UserMe_Repo.getInstance().gettoken(),filedata).enqueue(object :
            Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                try {
                    if (response.isSuccessful){
                        val jsonObject = JSONObject(response.body().toString())
                        val jsonArray = JSONArray(jsonObject.getString("location"))
                        callback.imageUploadCallback(true, jsonArray)
                    }
                    else {
                        callback.imageUploadCallback(false,JSONArray())
                    }
                }
                catch (e : JSONException){
                    callback.imageUploadCallback(false, JSONArray())
                }
            }
            override fun onFailure(call: Call<String?>, t: Throwable) {
                callback.imageUploadCallback(false, JSONArray())
            }
        })

    }


}