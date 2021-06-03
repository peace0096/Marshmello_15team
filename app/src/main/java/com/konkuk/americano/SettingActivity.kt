package com.konkuk.americano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konkuk.americano.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySettingBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
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

                //val intent = Intent(this@SettingActivity, ProfileEditActivity::class.java)
                //startActivity(intent)

            }

            settingMystoreList.settingBtnBtn.setOnClickListener {
                //val intent = Intent(this@SettingActivity,MyStoreActivity::class.java)
                //startActivity(intent)
            }

            settingMyreviewList.settingBtnBtn.setOnClickListener {
                //val intent = Intent(this@SettingActivity,CreateReviewActivity::class.java)
                //startActivity(intent)

            }



        }

    }
}