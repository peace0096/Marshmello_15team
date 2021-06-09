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
import com.konkuk.americano.Model.Store_Model
import com.konkuk.americano.Repo.Editstore_Repo
import gun0912.tedimagepicker.builder.TedImagePicker
import org.json.JSONArray
import org.json.JSONObject

class EditStoreViewModel(val context : Context, val activity : Activity) : ViewModel()  {

    data class Image_model(var bitmap : Bitmap?, var stringsrc : String?) {
    }

    var image1 = MutableLiveData<Image_model>()
    var image2 = MutableLiveData<Image_model>()
    var image3 = MutableLiveData<Image_model>()
    var content = MutableLiveData<String?>()
    var title = MutableLiveData<String?>()



    init{
        image1.value = Image_model(null,null)
        image2.value = Image_model(null,null)
        image3.value = Image_model(null,null)

        callStoreDetailAPI()

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


    private fun callStoreDetailAPI(){
        Editstore_Repo.getInstance().callGetStoreDetailAPI(object : RetrofitClient.callback {
            override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                if (isSuccessful && result != null){
                    var jsonobject = JSONObject(result)
                    val gson = Gson()
                    Editstore_Repo.getInstance().setmodel(gson.fromJson(jsonobject.toString(),Store_Model::class.java))
                    val data = Editstore_Repo.getInstance().getmodel()
                    val imgarr = arrayOf(image1,image2,image3)
                    for (i in 0 until data.image.size) {
                        imgarr[i].postValue(Image_model(null,data.image[i]))
                    }
                    content.postValue(data.content)
                    title.postValue(data.title)
                }
                else {
                    Toast.makeText(context,"잠시 후 시도 해주세요",Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
            }
        })
    }


    fun callEditStoreAPI(title : String, content : String){
        val imgarr = arrayOf(image1,image2,image3)

        var bitarray = ArrayList<Bitmap>()
        var imsstringarray = ArrayList<String>()

        for (it in imgarr){
            if (it.value!!.bitmap != null){
                bitarray.add(it.value!!.bitmap!!)
            }
            else if (it.value!!.stringsrc != null){
                imsstringarray.add(it.value!!.stringsrc!!)
            }
        }



        if (bitarray.size == 0 && imsstringarray.size == 0){
            Toast.makeText(context,"사진을 하나 이상 올려주세요",Toast.LENGTH_SHORT).show()
            return
        }

        UploadImageAPI.call(context,bitarray,object : RetrofitClient.callback{
            override fun imageUploadCallback(isSuccessful: Boolean, result: JSONArray) {
                if (isSuccessful){
                    for ( i in 0 until result.length() ){
                        imsstringarray.add(result.get(i) as String)
                    }
                    var resultarray = JSONArray(imsstringarray)

                    Editstore_Repo.getInstance().callPutStoreAPI(title,content,resultarray.toString(), object : RetrofitClient.callback {
                        override fun callbackMethod(isSuccessful: Boolean, result: String?) {
                           if(isSuccessful){
                               activity.finish()
                               Toast.makeText(context,"매장 수정을 성공 하셨습니다.",Toast.LENGTH_SHORT).show()
                           }
                        }
                    })
                }
                else {
                    Toast.makeText(context,"매장 수정을 실패 하셨습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        })
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
                        image1.value = Image_model(BitmapFactory.decodeStream(istream),null)
                        istream.close()
                    }
                }
                1 -> {
                    if (istream != null) {
                        image2.value = Image_model(BitmapFactory.decodeStream(istream),null)
                        istream.close()
                    }
                }
                2 -> {
                    if (istream != null) {
                        image3.value = Image_model(BitmapFactory.decodeStream(istream),null)
                        istream.close()
                    }
                }
            }
        }
    }

    fun showTedImagepicker(index : Int){
        if (permissionChecks()){
            TedImagePicker.with(context).start {
                setImageWithIndex(it,index)
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