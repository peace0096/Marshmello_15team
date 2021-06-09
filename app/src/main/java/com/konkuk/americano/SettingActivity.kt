package com.konkuk.americano

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.konkuk.americano.CreateReview.CreateReviewActivity
import com.konkuk.americano.CreateStore.CreateStoreActivity
import com.konkuk.americano.MyStore.MyStoreActivity
import com.konkuk.americano.ProfileEdit.ProfileEditActivity
import com.konkuk.americano.ViewModel.SettingViewModel
import com.konkuk.americano.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewmodel : SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        viewmodel = SettingViewModel(this,this)
        setContentView(binding.root)

        init()
    }


    private fun init(){

        binding.apply{
            settingProfileEdit.settingBtnTitle.text = "프로플 수정하기 "
            settingMystoreList.settingBtnTitle.text = "내가 추가한 매장"
            settingMyreviewList.settingBtnTitle.text = "내가 쓴 후기들"
            settingLogout.settingBtnTitle.text = "로그아웃"
            settingResign.settingBtnTitle.text = "회원탈퇴"



            settingProfileEdit.settingBtnBtn.setOnClickListener {

                val intent = Intent(this@SettingActivity, ProfileEditActivity::class.java)
                startActivity(intent)

            }

            settingMystoreList.settingBtnBtn.setOnClickListener {
//                val intent = Intent(this@SettingActivity,CreateStoreActivity::class.java)
                val intent = Intent(this@SettingActivity,MyStoreActivity::class.java)
                startActivity(intent)
            }

            settingMyreviewList.settingBtnBtn.setOnClickListener {
                val intent = Intent(this@SettingActivity,CreateReviewActivity::class.java)
                startActivity(intent)

            }

            settingResign.settingBtnBtn.setOnClickListener {
                viewmodel.processResign()
            }
            settingLogout.settingBtnBtn.setOnClickListener {
                viewmodel.processLogout()
            }
        }

    }



}