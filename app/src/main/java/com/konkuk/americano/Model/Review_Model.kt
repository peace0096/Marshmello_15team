package com.konkuk.americano.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Review_Model(
    @SerializedName("reviewId")
    @Expose
    var reviewId : Int,
    @SerializedName("storeId")
    @Expose
    var storeId : Int,
    @SerializedName("userId")
    @Expose
    var userId : Int,
    @SerializedName("content")
    @Expose
    var content : String,
    @SerializedName("image")
    @Expose
    var image : ArrayList<String>,
    @SerializedName("createdAt")
    @Expose
    var createdAt : String,
    @SerializedName("flavor")
    @Expose
    var flavor : Double,
    @SerializedName("sour")
    @Expose
    var sour : Double,
    @SerializedName("bitter")
    @Expose
    var bitter : Double,
    @SerializedName("aftertaste")
    @Expose
    var aftertaste : Double,
    @SerializedName("zest")
    @Expose
    var zest : Double,
    @SerializedName("balance")
    @Expose
    var balance : Double,
    @SerializedName("profileImage")
    @Expose
    var profileImage : ArrayList<String>,
    @SerializedName("nickname")
    @Expose
    var nickname : String
)
