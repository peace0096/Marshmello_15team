package com.konkuk.americano.CreateStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konkuk.americano.databinding.ActivityCreateReviewBinding
import com.konkuk.americano.databinding.ActivityCreateStoreBinding

class CreateStoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateStoreBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }


}