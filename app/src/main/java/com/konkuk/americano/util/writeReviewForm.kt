package com.konkuk.americano.util

import java.io.Serializable

data class writeReviewForm(
    val storeId: Int, val content: String, val image: String,
    val flavour: Int, val sour: Int, val bitter: Int,
    val aftertaste: Int, val zest: Int, val balance: Int
): Serializable
