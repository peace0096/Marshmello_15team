package com.konkuk.americano.CreateReview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.konkuk.americano.databinding.ActivityCreateReviewBinding


class CreateReviewActivity : AppCompatActivity() {


    private lateinit var binding : ActivityCreateReviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }




    private fun init(){
        binding.apply {
            createReviewFlavor.createReviewSeekbarboxSeekbar.max = 10
            createReviewFlavor.createReviewSeekbarboxSeekbar.min = 0

            createReviewFlavor.createReviewSeekbarboxSeekbar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val value = (progress / 2.0f)

                   createReviewFlavor.createReviewSeekbarboxValue.text = "(" + value.toString() + " / 5.0)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }


            })

            createReviewSour.createReviewSeekbarboxSeekbar.max = 10
            createReviewSour.createReviewSeekbarboxSeekbar.min = 0
            createReviewSour.createReviewSeekbarboxTitle.text = "신맛"
            createReviewSour.createReviewSeekbarboxSeekbar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val value = (progress / 2.0f)

                    createReviewSour.createReviewSeekbarboxValue.text = "(" + value.toString() + " / 5.0)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }


            })


            createReviewBitter.createReviewSeekbarboxSeekbar.max = 10
            createReviewBitter.createReviewSeekbarboxSeekbar.min = 0
            createReviewBitter.createReviewSeekbarboxTitle.text = "쓴맛"
            createReviewBitter.createReviewSeekbarboxSeekbar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val value = (progress / 2.0f)

                    createReviewBitter.createReviewSeekbarboxValue.text = "(" + value.toString() + " / 5.0)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }


            })


            createReviewAftertaste.createReviewSeekbarboxSeekbar.max = 10
            createReviewAftertaste.createReviewSeekbarboxSeekbar.min = 0
            createReviewAftertaste.createReviewSeekbarboxTitle.text = "뒷맛"
            createReviewAftertaste.createReviewSeekbarboxSeekbar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val value = (progress / 2.0f)

                    createReviewAftertaste.createReviewSeekbarboxValue.text = "(" + value.toString() + " / 5.0)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }


            })


            createReviewZest.createReviewSeekbarboxSeekbar.max = 10
            createReviewZest.createReviewSeekbarboxSeekbar.min = 0
            createReviewZest.createReviewSeekbarboxTitle.text = "풍미"
            createReviewZest.createReviewSeekbarboxSeekbar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val value = (progress / 2.0f)

                    createReviewZest.createReviewSeekbarboxValue.text = "(" + value.toString() + " / 5.0)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }


            })


            createReviewBalance.createReviewSeekbarboxSeekbar.max = 10
            createReviewBalance.createReviewSeekbarboxSeekbar.min = 0
            createReviewBalance.createReviewSeekbarboxTitle.text = "균형감"
            createReviewBalance.createReviewSeekbarboxSeekbar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val value = (progress / 2.0f)

                    createReviewBalance.createReviewSeekbarboxValue.text = "(" + value.toString() + " / 5.0)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })

            createReviewBackBtn.setOnClickListener {
                finish()
            }

        }
    }
}