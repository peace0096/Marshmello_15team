package com.konkuk.americano.ui.writereview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteReviewViewModel: ViewModel() {
    // temp
    private val _text = MutableLiveData<String>().apply {
        value = "This is writeReview Fragment"
    }
    val text: LiveData<String> = _text
}