package com.konkuk.americano.UI.EditStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.konkuk.americano.ViewModel.EditStoreViewModel
import com.konkuk.americano.databinding.ActivityEditStoreBinding


class EditStoreActivity : AppCompatActivity() {


    private lateinit var binding : ActivityEditStoreBinding
    private lateinit var viewmodel : EditStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStoreBinding.inflate(layoutInflater)
        viewmodel = EditStoreViewModel(this,this)
        setContentView(binding.root)
        init()
        initobserver()
    }



    private fun init(){
        binding.apply {
            editstoreBackBtn.setOnClickListener {
                finish()
            }

            editstoreImage1.setOnClickListener {
                viewmodel.showTedImagepicker(0)
            }
            editstoreImage2.setOnClickListener {
                viewmodel.showTedImagepicker(1)
            }
            editstoreImage3.setOnClickListener {
                viewmodel.showTedImagepicker(2)
            }

            editstorePostbtn.setOnClickListener {
                viewmodel.callEditStoreAPI(editstoreTitleEdit.text.toString(), editstoreContentEdit.text.toString())
            }

        }


    }


    private fun initobserver(){
        viewmodel.image1.observe(this, Observer {
            if (it.bitmap != null) {
                binding.editstoreImage1.setImageBitmap(it.bitmap)
            }
            else if (it.stringsrc != null){
                Glide.with(this)
                    .load(it.stringsrc)
                    .into(binding.editstoreImage1)
                binding.editstoreImage1.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        })

        viewmodel.image2.observe(this, Observer {
            if (it.bitmap != null) {
                binding.editstoreImage2.setImageBitmap(it.bitmap)
            }
            else if (it.stringsrc != null){
                Glide.with(this)
                    .load(it.stringsrc)
                    .into(binding.editstoreImage2)
                binding.editstoreImage2.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        })

        viewmodel.image3.observe(this, Observer {
            if (it.bitmap != null) {
                binding.editstoreImage3.setImageBitmap(it.bitmap)
            }
            else if (it.stringsrc != null){
                Glide.with(this)
                    .load(it.stringsrc)
                    .into(binding.editstoreImage3)
                binding.editstoreImage3.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        })



        viewmodel.title.observe(this, Observer{
            binding.editstoreTitleEdit.setText(it)
        })

        viewmodel.content.observe(this, Observer {
            binding.editstoreContentEdit.setText(it)
        })


    }



}



