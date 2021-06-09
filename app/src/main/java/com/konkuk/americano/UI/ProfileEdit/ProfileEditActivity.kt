package com.konkuk.americano.UI.ProfileEdit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.konkuk.americano.ViewModel.ProfileEditViewModel
import com.konkuk.americano.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {


    private lateinit var  binding : ActivityProfileEditBinding

    private lateinit var viewmodel : ProfileEditViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)

        viewmodel = ProfileEditViewModel(this,this)
        setContentView(binding.root)
        init()
        initobserver()
    }

    private fun init(){
        binding.apply {
            profileeditBackBtn.setOnClickListener{
                finish()
            }
            profileeditProfileImageEditbtn.setOnClickListener {
                viewmodel.showTedImagepicker()
            }

            profileeditPostBtn.setOnClickListener {
                viewmodel.callUserPutProfileAPI(profileeditNicknameEdittext.text.toString())
            }
        }
    }

    private fun initobserver(){
        viewmodel.image1.observe(this, Observer {
            if (it.bitmap != null){
                binding.profileeditProfileImage.setImageBitmap(it.bitmap)
            }
            else if (it.stringsrc != null ) {
                Glide.with(this)
                    .load(it.stringsrc)
                    .into( binding.profileeditProfileImage)
            }
            binding.profileeditProfileImage.scaleType = ImageView.ScaleType.CENTER_CROP
        })
        viewmodel.nickname.observe(this, Observer {
            binding.profileeditNicknameEdittext.setText(it)
        })
    }
}