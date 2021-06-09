package com.konkuk.americano.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StoreDetailData(
    @SerializedName("storeId")
    @Expose
    val storeId: Int,
    @SerializedName("userId")
    @Expose
    val userId: Int,
    @SerializedName("image")
    @Expose
    val image: ArrayList<String>,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("content")
    @Expose
    val content: String,
    @SerializedName("latitude")
    @Expose
    val latitude: Double,
    @SerializedName("longitude")
    @Expose
    val longitude: Double,
    @SerializedName("flavor")
    @Expose
    val flavor: Double,
    @SerializedName("sour")
    @Expose
    val sour: Double,
    @SerializedName("bitter")
    @Expose
    val bitter: Double,
    @SerializedName("aftertaste")
    @Expose
    val aftertaste: Double,
    @SerializedName("zest")
    @Expose
    val zest: Double,
    @SerializedName("balance")
    @Expose
    val balance: Double,
    @SerializedName("createdAt")
    @Expose
    val createdAt: String
)
