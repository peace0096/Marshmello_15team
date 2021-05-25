package com.konkuk.americano.util

import android.graphics.Bitmap
import kotlin.collections.ArrayList

data class Review(
    val shopName: String,
    val detailContent: String,
    val statistics: MutableMap<Evaluations, Int>,
    val images: ArrayList<Bitmap>,
    val userIcon: Bitmap,
    val userNick: String,
    val date: String
)
