package com.konkuk.americano.UI.LoginRegister

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.konkuk.americano.ViewModel.UserViewModel
import com.konkuk.americano.databinding.ActivityRegisterBinding
import gun0912.tedimagepicker.builder.TedImagePicker

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding:ActivityRegisterBinding
    private var image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionbar()
        initObserve()
        init()
    }

    private fun initObserve() {
        viewModel = UserViewModel(baseContext)
        viewModel.tokenmodel.observe(this, Observer {
            if(it != null) {
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun initActionbar() {
        val actionBar = supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_background)
    }

    private fun init() {
        binding.apply {
            registerBtn.setOnClickListener {
                val id = idEditText.text.toString()
                val password = passwordEditText.text.toString()
                val password_r = passwordEditText1.text.toString()
                val nickname = nicknameEditText.text.toString()
                if(idEditText.text!!.isEmpty()) {
                    idEditText.error = "아이디 필수!"
                    return@setOnClickListener
                }else {
                    idEditText.error = null
                }
                if(passwordEditText.text!!.isEmpty()) {
                    passwordEditText.error = "비밀번호 필수!"
                    return@setOnClickListener
                }else {
                    passwordEditText.error = null
                }
                if(passwordEditText1.text!!.isEmpty()) {
                    passwordEditText1.error = "비밀번호 다시 입력!"
                    return@setOnClickListener
                }else {
                    passwordEditText1.error = null
                }
                if(nicknameEditText.text!!.isEmpty()) {
                    nicknameEditText.error = "닉네임 필수!"
                    return@setOnClickListener
                }else {
                    nicknameEditText.error = null
                }
                if(password != password_r) {
                    passwordEditText1.error = "비밀번호가 서로 다름!"
                    return@setOnClickListener
                }else {
                    passwordEditText.error = null
                }
                var profileImage:Bitmap? = null
                if(image != null)
                    profileImage = image
                val latitude = 0.0
                val longitude = 0.0
                viewModel.registerImage(id, password, nickname, profileImage, latitude, longitude)

            }

            registerBackBtn.setOnClickListener {
                finish()
            }

            addProfile.setOnClickListener {
                if(permissionChecks()) {

                    TedImagePicker.with(this@RegisterActivity).start {
                        if(it == null) {
                            Toast.makeText(baseContext, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            val istream = baseContext.contentResolver.openInputStream(it)
                            val bitmap = BitmapFactory.decodeStream(istream)
                            image = bitmap
                            profile.setImageBitmap(bitmap)
                        }

                    }
                }
            }



        }
    }
    private fun permissionChecks() : Boolean {
        if (ActivityCompat.checkSelfPermission(baseContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        else {
            requestCameraPermission()
            return false
        }

    }
    val CAMERA_REQUEST = 100

    private fun requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
    }
}
