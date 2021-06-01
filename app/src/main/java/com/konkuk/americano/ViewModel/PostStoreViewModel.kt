package com.konkuk.americano.ViewModel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.gun0912.tedpermission.PermissionListener
import com.konkuk.americano.API.List.PostStoreAPI
import com.konkuk.americano.API.List.UploadImageAPI
import com.konkuk.americano.API.RetrofitClient
import gun0912.tedimagepicker.builder.TedImagePicker
import org.json.JSONArray


class PostStoreViewModel(val context : Context,val activity : Activity) : ViewModel() {

    var image1 = MutableLiveData<Bitmap?>()
    var image2 = MutableLiveData<Bitmap?>()
    var image3 = MutableLiveData<Bitmap?>()

    private var imgarr = ArrayList<MutableLiveData<Bitmap?>>()




    init {
        image1.value = null
        image2.value = null
        image3.value = null



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





    private fun setImages(uriList: List<Uri>) {
        // Log.i("urilistsize", uriList.size.toString())
        // 3 장 초과 체크 했을 경우 -> 알림 메시지 띄우고 3개만 이미지 추가
        if (uriList.size > 3) {
            Toast.makeText(context, "이미지는 3장까지 선택가능합니다.", Toast.LENGTH_SHORT).show()
        }
        if (uriList.isEmpty()) {
            Toast.makeText(context, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
        } else {
            imgarr.clear()
            imgarr.add(image1)
            imgarr.add(image2)
            imgarr.add(image3)
            for (i in 0..uriList.size) {
                val istream = context.contentResolver.openInputStream(uriList[i])
                if (istream != null) {
                    imgarr[i].value = BitmapFactory.decodeStream(istream)
                    istream.close()
                }
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
                        image1.value = BitmapFactory.decodeStream(istream)
                        istream.close()
                    }
                }
                1 -> {
                    if (istream != null) {
                        image2.value = BitmapFactory.decodeStream(istream)
                        istream.close()
                    }
                }
                2 -> {
                    if (istream != null) {
                        image3.value = BitmapFactory.decodeStream(istream)
                        istream.close()
                    }
                }
            }
        }
    }

     fun showTedImagepicker(isMulti : Boolean, index : Int){
         if (permissionChecks()){
             if (isMulti){
                 TedImagePicker.with(context).startMultiImage {
                     setImages(it)
                 }
             }
             else {
                 TedImagePicker.with(context).start {
                     setImageWithIndex(it,index)
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
        else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
    }



    fun callPostUploadAPI(title : String, content : String){
        imgarr.clear()
        imgarr.add(image1)
        imgarr.add(image2)
        imgarr.add(image3)
        var bitarray = ArrayList<Bitmap>()
        for (it in imgarr){
            if (it.value != null){
                bitarray.add(it.value!!)
            }
        }

        if (bitarray.size == 0){
            Toast.makeText(context,"사진을 하나 이상 올려주세요",Toast.LENGTH_SHORT).show()
            return
        }

        UploadImageAPI.call(context,bitarray,object : RetrofitClient.callback{
            override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                if (isSuccessful){
                    PostStoreAPI.call(title,content,37.0 ,127.0,result.toString(), object : RetrofitClient.callback {
                        override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                            if (isSuccessful){
                                Toast.makeText(context,"매장 등록을 성공 하셨습니다.",Toast.LENGTH_SHORT).show()
                                activity.finish()
                            }
                        }
                    })
                }
                else {
                    Toast.makeText(context,"매장 등록을 실패 하셨습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



}