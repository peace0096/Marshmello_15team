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
    @POST("user/login")
    fun login(
        @Body body:String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @POST("user/register")
    fun register(
        @Body body:String
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


    @Headers("Content-Type: application/json")
    @GET("store/mystore")
    fun getmystore(
        @Header("Authorization") token : String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("store/")
    fun getstorelocation(
        @Header("Authorization") token : String,
        @Query("latitude") latitude:Double,
        @Query("longitude") longitude:Double
    ): Call<String?>


    @Headers("Content-Type: application/json")
    @PUT("store/")
    fun putmystore(
        @Header("Authorization") token : String,
        @Body body: String
    ): Call<String?>


    @Headers("Content-Type: application/json")
    @GET("store/detail")
    fun getstoredetail(
        @Header("Authorization") token : String,
        @Query("storeId") storeId : Int
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("review/store")
    fun getStoreReviews(
        @Header("Authorization") token: String,
        @Query("storeId") storeId: Int
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @POST("review/")
    fun postWriteReview(
        @Header("Authorization") token: String,
        @Body body: String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("review/me")
    fun getMyReviews(
        @Header("Authorization") token: String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @GET("review/recent")
    fun getRecentReview(
        @Header("Authorization") token: String
    ): Call<String?>


    @Headers("Content-Type: application/json")
    @DELETE("user/")
    fun resign(
        @Header("Authorization") token : String
    ): Call<String?>

    @Headers("Content-Type: application/json")
    @PUT("user/location")
    fun updateUserLocation(
        @Header("Authorization") token : String,
        @Body body: String
    ): Call<String?>


    @Headers("Content-Type: application/json")
    @GET("review/me")
    fun getmyreview(
        @Header("Authorization") token : String
    ): Call<String?>



}