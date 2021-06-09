package com.konkuk.americano.ViewModel

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Repo.UserMe_Repo


class SettingViewModel(val context : Context, val activity : Activity) : ViewModel() {


    fun processResign(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("회원탈퇴 ");
        builder.setMessage("회원 탈퇴를 하시겠습니까?");
        builder.setPositiveButton(
            "탈퇴하기"
        ) { dialog, id ->
            callResignAPI()
            dialog.dismiss()
        }

        builder.setNegativeButton(
            "취소"
        ) { dialog, id ->
            dialog.dismiss()
        }
        builder.show()

    }


    fun processLogout(){

        val builder = AlertDialog.Builder(context)
        builder.setTitle("로그아웃");
        builder.setMessage("로그 아웃을  하시겠습니까?");
        builder.setPositiveButton(
            "로그아웃"
        ) { dialog, id ->
            //로그인 화면으로 돌아 가야 됨
            TODO("로그인 화면으로 가야함 ")
            dialog.dismiss()
        }

        builder.setNegativeButton(
            "취소"
        ) { dialog, id ->
            dialog.dismiss()
        }
        builder.show()

    }

    private fun callResignAPI(){
        UserMe_Repo.getInstance().callDeleteUserAPI(object : RetrofitClient.callback{
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if (isSuccessful){
                    Toast.makeText(context, "회원탈퇴 되셨습니다",Toast.LENGTH_SHORT).show()

                    TODO("로그인 화면으로 가야함 ")
                }
            }

        })
    }






}
