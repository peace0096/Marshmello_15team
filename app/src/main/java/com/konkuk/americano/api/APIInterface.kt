package com.konkuk.americano.api

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
    @POST("user/login")
    fun login(
        @Body body:String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @POST("user/register")
    fun register(
        @Body body:String
    ): Call<String?>

    @Multipart
    @POST("upload")
    fun uploadPhoto(
        @Header("Authorization") token: String,
        @Part file: ArrayList<MultipartBody.Part>
    ): Call<String?>

    /*******************************************************************/

    @Headers("Content-Type: application/json")
    @GET("review/me")
    fun getMyReviews(
        @Header("Authorization") token: String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @POST("review/")
    fun postWriteReview(
        @Header("Authorization") token: String,
        @Body body: String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("store/detail")
    fun getStoreDetail(
        @Header("Authorization") token: String,
        @Query("storeId") storeId: Int
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("review/store")
    fun getStoreReviews(
        @Header("Authorization") token: String,
        @Query("storeId") storeId: Int
    ): Call<String?>


}

