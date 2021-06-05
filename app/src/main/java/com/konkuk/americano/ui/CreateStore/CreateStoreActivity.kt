package com.konkuk.americano.ui.CreateStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.konkuk.americano.ViewModel.PostStoreViewModel
import com.konkuk.americano.databinding.ActivityCreateStoreBinding

class CreateStoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateStoreBinding


    private lateinit var viewmodel : PostStoreViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateStoreBinding.inflate(layoutInflater)
        viewmodel = PostStoreViewModel(this,this)

        setContentView(binding.root)

        init()
        setObserver()

    }


    private fun init(){
        binding.apply {
            createstoreImage1.setOnClickListener {
                viewmodel.showTedImagepicker(false,0)
            }
            createstoreImage2.setOnClickListener {
                viewmodel.showTedImagepicker(false,1)
            }
            createstoreImage3.setOnClickListener {
                viewmodel.showTedImagepicker(false,2)
            }

            createstoreBackBtn.setOnClickListener {
                finish()
            }
            createstorePostbtn.setOnClickListener {
                viewmodel.callPostUploadAPI(createstoreTitleEdit.text.toString(),createstoreContentEdit.text.toString())
            }

        }
    }

    private fun setObserver(){
        binding.apply {
            viewmodel.image1.observe(this@CreateStoreActivity, Observer {
                if (it != null){
                    createstoreImage1.setImageBitmap(it)
                    createstoreImage1.scaleType = ImageView.ScaleType.FIT_CENTER
                }
            })
            viewmodel.image2.observe(this@CreateStoreActivity, Observer {
                if (it != null){
                    createstoreImage2.setImageBitmap(it)
                    createstoreImage2.scaleType = ImageView.ScaleType.FIT_CENTER
                }
            })
            viewmodel.image3.observe(this@CreateStoreActivity, Observer {
                if (it != null){
                    createstoreImage3.setImageBitmap(it)
                    createstoreImage3.scaleType = ImageView.ScaleType.FIT_CENTER
                }
            })

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


}