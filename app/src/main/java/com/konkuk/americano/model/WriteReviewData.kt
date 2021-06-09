package com.konkuk.americano.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WriteReviewData(
    @SerializedName("storeId")
    @Expose
    val storeId: Int,
    @SerializedName("content")
    @Expose
    val content: String,
    @SerializedName("image")
    @Expose
    val image: ArrayList<String>,
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
    val balance: Double
)
