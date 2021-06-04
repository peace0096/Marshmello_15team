package com.konkuk.americano.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.konkuk.americano.MapActivity
import com.konkuk.americano.databinding.ActivityLoginBinding
import com.konkuk.americano.loginregister.UserLoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var userLoginViewModel : UserLoginViewModel
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserve()
        init()
    }

    private fun initObserve() {
        userLoginViewModel = UserLoginViewModel()

        userLoginViewModel.tokenmodel.observe(this, Observer {
            if(it != null) {
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    //TODO 로그인 예외처리 해야함
    private fun init() {
        binding.apply {
            loginBtn.setOnClickListener {
                val id = idEditText.text.toString()
                val password = passwordEditText.text.toString()
                userLoginViewModel.login(id, password)
            }


            registerText.setOnClickListener {
                val intent = Intent(baseContext, RegisterAcitivty::class.java)
                startActivity(intent)
            }

        }

    }
}