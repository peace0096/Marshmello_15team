package com.konkuk.americano.util

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat


class CameraPicker(val activity: Activity) {

    val REQUEST_IMAGE_CAPTURE = 1
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)


    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    fun checkPermission(permission: Array<out String>, flag: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permission) {
                if (activity.checkSelfPermission( permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions( arrayOf(permission),  flag)
                    return false
                }
            }
        }
        return true
    }


    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) : Bitmap? {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data.extras?.get("data") as Bitmap


            return  imageBitmap
        }

        else {
            return null
        }
    }



}