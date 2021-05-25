package com.konkuk.americano.ui.storedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoreDetailViewModel: ViewModel() {
    // temp
    private val _text = MutableLiveData<String>().apply {
        value = "This is store detail Fragment"
    }
    val text: LiveData<String> = _text
}