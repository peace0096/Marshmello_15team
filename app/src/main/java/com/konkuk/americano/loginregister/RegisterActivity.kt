package com.konkuk.americano.loginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.konkuk.americano.R
import com.konkuk.americano.databinding.ActivityRegisterBinding
import com.konkuk.americano.loginregister.UserLoginViewModel

class RegisterAcitivty : AppCompatActivity() {
    lateinit var viewModel: UserLoginViewModel
    lateinit var binding:ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionbar()
        initObserve()
        init()
    }

    private fun initObserve() {
        viewModel = UserLoginViewModel()
        viewModel.tokenmodel.observe(this, Observer {
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
                viewModel.register(id, password, nickname, profileImage, latitutde, longitude)

            }

        }
    }
}