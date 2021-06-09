package com.konkuk.americano.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Store_Model(
    @SerializedName("storeId")
    @Expose
    var storeId : Int,
    @SerializedName("image")
    @Expose
    var image : ArrayList<String>,
    @SerializedName("title")
    @Expose
    var title : String,
    @SerializedName("content")
    @Expose
    var content : String,
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
    @SerializedName("createdAt")
    @Expose
    var createdAt : String,
    @SerializedName("latitude")
    @Expose
    var latitude : Double,
    @SerializedName("longitude")
    @Expose
    var longitude : Double)