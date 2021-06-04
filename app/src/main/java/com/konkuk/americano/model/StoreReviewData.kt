package com.konkuk.americano.model

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
    val flavor: Int,
    @SerializedName("sour")
    @Expose
    val sour: Int,
    @SerializedName("bitter")
    @Expose
    val bitter: Int,
    @SerializedName("aftertaste")
    @Expose
    val aftertaste: Int,
    @SerializedName("zest")
    @Expose
    val zest: Int,
    @SerializedName("balance")
    @Expose
    val balance: Int,
    @SerializedName("nickname")
    @Expose
    val nickname: String,
    @SerializedName("profileImage")
    @Expose
    val profileImage: ArrayList<String>
)
