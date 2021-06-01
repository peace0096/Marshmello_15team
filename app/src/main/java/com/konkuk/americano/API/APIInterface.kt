package com.konkuk.americano.API

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {

    @Headers("Content-Type: application/json")
    @PUT("user/")
    fun putUser(
        @Header("Authorization") token : String,
        @Body body: String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("user/me")
    fun getuserme(
        @Header("Authorization") token : String
    ): Call<String?>




    @Headers("Content-Type: application/json")
    @POST("store")
    fun poststore(
        @Header("Authorization") token : String,
        @Body body: String
    ): Call<String?>


    @Multipart
    @POST("upload")
    fun uploadPhoto(
        @Header("Authorization") token: String,
        @Part file: ArrayList<MultipartBody.Part>
    ): Call<String?>



}