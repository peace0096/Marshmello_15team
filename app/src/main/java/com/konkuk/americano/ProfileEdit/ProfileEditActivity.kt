package com.konkuk.americano.ProfileEdit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.konkuk.americano.Util.CameraPicker
import com.konkuk.americano.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileEditViewModel
    private lateinit var  binding : ActivityProfileEditBinding
    private lateinit var picker :  CameraPicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        picker = CameraPicker(this)
        viewModel = ProfileEditViewModel()
        init()
    }

    private fun init(){


        binding.apply {

            viewModel.usermemodel.observe(this@ProfileEditActivity, Observer {
                if(it != null) {

                }
            })

            profileeditBackBtn.setOnClickListener{
                finish()
            }


            profileeditProfileImageEditbtn.setOnClickListener {


                if (picker.checkPermission(picker.CAMERA_PERMISSION,picker.FLAG_PERM_CAMERA)){
                    picker.dispatchTakePictureIntent()
                }
                else {
                    Log.i("dfasdf","asdfasdfasdfasdf")
                }

            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null){
           binding.profileeditProfileImage.setImageBitmap(picker.onActivityResult(requestCode,resultCode,data))
        }


    }
}