package com.konkuk.americano.ViewModel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.gun0912.tedpermission.PermissionListener
import com.konkuk.americano.API.List.UploadImageAPI
import com.konkuk.americano.API.RetrofitClient
import com.konkuk.americano.Model.UserMe_Model
import com.konkuk.americano.Repo.UserMe_Repo
import gun0912.tedimagepicker.builder.TedImagePicker
import org.json.JSONArray


class ProfileEditViewModel(val context : Context, val activity : Activity) :ViewModel() {

    data class Image_model(var bitmap : Bitmap?, var stringsrc : String?) {
    }

    var image1 = MutableLiveData<Image_model>()
    var nickname = MutableLiveData<String>()


    init {
        callUserMeModelAPI()

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    context,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


    private fun callUserMeModelAPI(){
        UserMe_Repo.getInstance().callGetUserMeAPI(object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                var gson = Gson()
                UserMe_Repo.getInstance().setModel(gson.fromJson(result,UserMe_Model::class.java))

                image1.value = Image_model(null,UserMe_Repo.getInstance().getModel().profileImage[0])
                nickname.value = UserMe_Repo.getInstance().getModel().nickname
            }
        })
    }


    fun callUserPutProfileAPI(nickname : String){

        if (image1.value!!.bitmap != null){
            var arr = ArrayList<Bitmap>()
            arr.add(image1.value!!.bitmap!!)
            UploadImageAPI.call(context,arr,object : RetrofitClient.callback {
                override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                    if (isSuccessful) {
                        UserMe_Repo.getInstance().callUserPutAPI(nickname,result.toString(),object : RetrofitClient.callback {
                            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                                if (isSuccessful) {
                                    callUserMeModelAPI()
                                    Toast.makeText(context,"정보가 수정되었습니다.",Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    Toast.makeText(context,"정보 수정에 실패했습니다.",Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }
                    else {
                        Toast.makeText(context,"정보 수정에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        else {
            var resultarray = JSONArray(UserMe_Repo.getInstance().getModel().profileImage)
            UserMe_Repo.getInstance().callUserPutAPI(nickname,resultarray.toString(),object : RetrofitClient.callback {
                override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                    if (isSuccessful) {
                        callUserMeModelAPI()
                    }
                    else {
                        Toast.makeText(context,"정보 수정에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }

    fun showTedImagepicker(){
        if (permissionChecks()){
            TedImagePicker.with(context).start {
                setImageWithIndex(it,0)
            }
        }
    }

    private fun setImageWithIndex(uri : Uri, index : Int){

        if (uri == null){
            Toast.makeText(context, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
        else {
            val istream = context.contentResolver.openInputStream(uri)
            when(index) {
                0 -> {
                    if (istream != null) {
                        image1.value = Image_model(BitmapFactory.decodeStream(istream), null)
                        istream.close()
                    }
                }
            }
        }
    }


    private fun permissionChecks() : Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        else {
            requestCameraPermission()
            return false
        }

    }
    val CAMERA_REQUEST = 100

    private fun requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
        else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
    }








}
