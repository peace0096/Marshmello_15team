package com.konkuk.americano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konkuk.americano.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {


    private lateinit var  binding : ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.apply {
            profileeditBackBtn.setOnClickListener{
                finish()
            }


        }
    }
}