package com.konkuk.americano.EditStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konkuk.americano.databinding.ActivityEditStoreBinding


class EditStoreActivity : AppCompatActivity() {


    private lateinit var binding : ActivityEditStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }



    private fun init(){
        binding.apply {
            editstoreBackBtn.setOnClickListener {
                finish()
            }

        }


    }



}



