package com.konkuk.americano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.konkuk.americano.databinding.ActivityRegisterAcitivtyBinding
import com.konkuk.americano.service.User
import com.konkuk.americano.service.UserViewModel

class RegisterAcitivty : AppCompatActivity() {
    lateinit var binding:ActivityRegisterAcitivtyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionbar()
        initObserve()
        init()
    }

    private fun initObserve() {
        UserViewModel.registerLiveData.observe(this, Observer {
            if(it != null) {
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun initActionbar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_background)
    }


    private fun init() {
        binding.apply {
            registerBtn.setOnClickListener {
                //TODO 비밀번호 에러메세지 추가해야할 것
                val id = idEditText.text.toString()
                val password = passwordEditText.text.toString()
                val nickname = nicknameEditText.text.toString()
                val profileImage = "[]"
                val latitutde = 0.0
                val longitude = 0.0
                val user = User(id, password, nickname, profileImage, latitutde, longitude)
                UserViewModel.registerId(user)

            }

        }
    }
}