package com.konkuk.americano.UI.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.konkuk.americano.UI.Map.MapActivity
import com.konkuk.americano.ViewModel.UserViewModel
import com.konkuk.americano.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var userViewModel : UserViewModel
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserve()
        init()
    }

    private fun initObserve() {
        userViewModel = UserViewModel(this)

        userViewModel.tokenmodel.observe(this, Observer {
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
                userViewModel.login(id, password)
            }


            registerText.setOnClickListener {
                val intent = Intent(baseContext, RegisterActivity::class.java)
                startActivity(intent)
            }

        }

    }
}