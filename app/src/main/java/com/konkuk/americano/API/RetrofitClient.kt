package com.konkuk.americano.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {

    interface callback {
        fun callbackMethod(isSuccessful: Boolean, result: String?){}
        fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray){}
    }

    private lateinit var retrofit: Retrofit



    fun getBaseClient() : APIInterface {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val url = "http://15.165.96.180:7782/api/"
//            "http://192.168.50.161:7782/api/"


        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(APIInterface::class.java)
    }

}

