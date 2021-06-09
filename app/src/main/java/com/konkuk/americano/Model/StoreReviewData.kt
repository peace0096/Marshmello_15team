package com.konkuk.americano.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StoreReviewData(
    @SerializedName("reviewId")
    @Expose
    val reviewId: Int,
    @SerializedName("storeId")
    @Expose
    val storeId: Int,
    @SerializedName("content")
    @Expose
    val content: String,
    @SerializedName("image")
    @Expose
    val image: ArrayList<String>,
    @SerializedName("createdAt")
    @Expose
    val createdAt: String,
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
    @SerializedName("nickname")
    @Expose
    val nickname: String,
    @SerializedName("profileImage")
    @Expose
    val profileImage: ArrayList<String>
)