package com.konkuk.americano.ui.LoginRegister

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.konkuk.americano.R
import com.konkuk.americano.ViewModel.UserViewModel
import com.konkuk.americano.databinding.ActivityRegisterAcitivtyBinding
import gun0912.tedimagepicker.builder.TedImagePicker

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding:ActivityRegisterAcitivtyBinding
    private var image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAcitivtyBinding.inflate(layoutInflater)
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
                val profileImage = image

                val latitutde = 0.0
                val longitude = 0.0
                viewModel.register(id, password, nickname, profileImage, latitutde, longitude)

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