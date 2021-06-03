package com.konkuk.americano.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserMe_Model (
    @SerializedName("loginId")
    @Expose
    var loginId:String?,
    @SerializedName("password")
    @Expose
    var password:String?,
    @SerializedName("nickname")
    @Expose
    var nickname:String?,
    @SerializedName("profileImage")
    @Expose
    var profileImage:String?,
    @SerializedName("latitude")
    @Expose
    var latitude:Double?,
    @SerializedName("longitude")
    @Expose
    var langitude:Double?)
