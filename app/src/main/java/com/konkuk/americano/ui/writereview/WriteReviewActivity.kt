package com.konkuk.americano.ui.writereview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.listItems
import com.konkuk.americano.R
import com.konkuk.americano.databinding.ActivityWriteReviewBinding
import com.konkuk.americano.model.WriteReviewData
import com.konkuk.americano.viewmodel.WriteReviewViewModel
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.check_write_review_dialog.view.*
import kotlinx.android.synthetic.main.create_review_seekbar_box.view.*

class WriteReviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWriteReviewBinding

    lateinit var viewModel: WriteReviewViewModel

    var storeId = -1

    var flavorValue = 1.0
    var sourValue = 1.0
    var bitterValue = 1.0
    var aftertasteValue = 1.0
    var zestValue = 1.0
    var balanceValue = 1.0

    var imageUri1: Uri? = null
    var imageUri2: Uri? = null
    var imageUri3: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        //for test
//        val tData = WriteReviewData(
//            3,
//            "TEST REVIEW22222",
//            arrayListOf(),
//            4.0,
//            5.0,
//            2.0,
//            3.0,
//            3.0,
//            1.0
//        )
//        viewModel.callPostWriteReviewAPI(tData)
    }

    private fun init() {
        initViewModel()

        initObserve()

        initSeekbar()

        initAddImagesBtn()

        initWriteBtn()
    }

    private fun initObserve() {
        viewModel.responseInt.observe(this, Observer {
            if (viewModel.responseInt.value == 1) {
                Toast.makeText(this, "저장 성공!!!!!!!!!!!!", Toast.LENGTH_SHORT).show()
                //this.finish()
            } else {
                Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initViewModel() {
        viewModel = WriteReviewViewModel(this, this)
    }

    private fun initSeekbar() {
        // fromUser: boolean: True if the progress change was initiated by the user.
        class SeekBarChangeListener: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    val value = this.spiltValue(progress)
                    val string = "$value / 5.0"
                    when(seekBar.tag) {
                        1 -> {
                            binding.writeFlavorSeekbar.createReviewSeekbarboxValue.text = string
                            flavorValue = value
                        }
                        2 -> {
                            binding.writeSourSeekbar.createReviewSeekbarboxValue.text = string
                            sourValue = value
                        }
                        3 -> {
                            binding.writeBitterSeekbar.createReviewSeekbarboxValue.text = string
                            bitterValue = value
                        }
                        4 -> {
                            binding.writeAftertasteSeekbar.createReviewSeekbarboxValue.text = string
                            aftertasteValue = value
                        }
                        5 -> {
                            binding.writeZestSeekbar.createReviewSeekbarboxValue.text = string
                            zestValue = value
                        }
                        6 -> {
                            binding.writeBalanceSeekbar.createReviewSeekbarboxValue.text = string
                            balanceValue = value
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }
            private fun spiltValue(progress: Int): Double {
                // progress: 0-100 -> 0, 1, 2, 3, 4, 5
                val v = progress.toDouble() / 20
                return if (v < 0.5) {
                    0.0
                } else if (v < 1.0) {
                    0.5
                } else if (v < 1.5) {
                    1.0
                } else if (v < 2.0) {
                    1.5
                } else if (v < 2.5) {
                    2.0
                } else if (v < 3.0) {
                    2.5
                } else if (v < 3.5) {
                    3.0
                } else if (v < 4.0) {
                    3.5
                } else if (v < 4.5) {
                    4.0
                } else if (v <5.0) {
                    4.5
                } else {
                    5.0
                }
            }
        }

        binding.apply {
            writeReviewEvaluationLayout.apply {
                for (i in 0 until childCount) {
                    getChildAt(i).apply {
                        when(id) {
                            R.id.writeFlavorSeekbar -> {
                                create_review_seekbarbox_title.text = "향미"
                                create_review_seekbarbox_seekbar.tag = 1 // callback 함수 위해서
                            }
                            R.id.writeSourSeekbar -> {
                                create_review_seekbarbox_title.text = "신맛"
                                create_review_seekbarbox_seekbar.tag = 2
                            }
                            R.id.writeBitterSeekbar -> {
                                create_review_seekbarbox_title.text = "쓴맛"
                                create_review_seekbarbox_seekbar.tag = 3
                            }
                            R.id.writeAftertasteSeekbar -> {
                                create_review_seekbarbox_title.text = "뒷맛"
                                create_review_seekbarbox_seekbar.tag = 4
                            }
                            R.id.writeZestSeekbar -> {
                                create_review_seekbarbox_title.text = "풍미"
                                create_review_seekbarbox_seekbar.tag = 5
                            }
                            R.id.writeBalanceSeekbar -> {
                                create_review_seekbarbox_title.text = "균형감"
                                create_review_seekbarbox_seekbar.tag = 6
                            }
                        }
                    }
                    getChildAt(i).create_review_seekbarbox_seekbar
                        .setOnSeekBarChangeListener(SeekBarChangeListener())
                }
            }
        }
    }

    private fun initAddImagesBtn() {
        // 갤러리 or 카메라로 이미지 선택 각각 버튼에 달아주기
        binding.imagePlusBtn.setOnClickListener {
            TedImagePicker.with(this)
                .startMultiImage {
                    uriList -> setReviewImages(uriList)
                }
        }
        binding.writeReviewImage1.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    setReviewImage(uri, it)
                    imageUri1 = uri
                }
        }
        binding.writeReviewImage2.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    setReviewImage(uri, it)
                    imageUri2 = uri
                }
        }
        binding.writeReviewImage3.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    setReviewImage(uri, it)
                    imageUri3 = uri
                }
        }
    }

    private fun setReviewImage(uri: Uri, view: View) {
        view as ImageView
        view.setImageURI(uri)
        view.background = null
    }

    private fun setReviewImages(uriList: List<Uri>) {
        // Log.i("urilistsize", uriList.size.toString())
        // 3 장 초과 체크 했을 경우 -> 알림 메시지 띄우고 3개만 이미지 추가
        if (uriList.size > 3) {
            Toast.makeText(this, "이미지는 3장까지 선택가능합니다.", Toast.LENGTH_SHORT).show()
        }
        if (uriList.isEmpty()) {
            Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
            imageUri1 = null
            imageUri2 = null
            imageUri3 = null
            binding.writeReviewImage1.background = getDrawable(R.drawable.image_select_box)
            binding.writeReviewImage2.background = getDrawable(R.drawable.image_select_box)
            binding.writeReviewImage3.background = getDrawable(R.drawable.image_select_box)
        } else {
            binding.writeReviewImage1.apply {
                setImageURI(uriList[0])
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                background = null
            }
            imageUri1 = uriList[0]
            imageUri2 = null
            imageUri3 = null
            if (uriList.size > 1) {
                binding.writeReviewImage2.apply {
                    setImageURI(uriList[1])
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    background = null
                }
                imageUri2 = uriList[1]
            } else {
                binding.writeReviewImage2.setImageURI(null)
                binding.writeReviewImage2.background = getDrawable(R.drawable.image_select_box)
            }
            if (uriList.size > 2) {
                binding.writeReviewImage3.apply {
                    setImageURI(uriList[2])
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    background = null
                }
                imageUri3 = uriList[2]
            } else {
                binding.writeReviewImage3.setImageURI(null)
                binding.writeReviewImage3.background = getDrawable(R.drawable.image_select_box)
            }
        }
    }

    private fun initWriteBtn() {
        binding.writeReviewWriteBtn.setOnClickListener {
            writeReview()
        }
    }


    // 작성하기 버튼 listener 성공 여부 반환
    private fun writeReview(){
        // 확인 창 띄우기!
        MaterialDialog(this, BottomSheet()).show {
            // default height: 60%
            cornerRadius(16f)

            val view: View = layoutInflater.inflate(R.layout.check_write_review_dialog, null)
            view.writeDialogTitle.text = "Store Title" // temp
            view.writeDialogFlavor.text = flavorValue.toString()
            view.writeDialogAftertaste.text = aftertasteValue.toString()
            view.writeDialogBalance.text = balanceValue.toString()
            view.writeDialogBitter.text = bitterValue.toString()
            view.writeDialogSour.text = sourValue.toString()
            view.writeDialogZest.text = zestValue.toString()
            view.writeDialogContent.text = binding.writeReviewContentEditText.text
            if (imageUri1 != null) {
                view.writeDialogImage1.setImageURI(imageUri1)
            } else { view.writeDialogImage1.visibility = View.INVISIBLE }
            if (imageUri2 != null) {
                view.writeDialogImage2.setImageURI(imageUri2)
            } else { view.writeDialogImage2.visibility = View.INVISIBLE }
            if (imageUri3 != null) {
                view.writeDialogImage3.setImageURI(imageUri3)
            } else { view.writeDialogImage3.visibility = View.INVISIBLE }

            customView(view = view)

            positiveButton(text = "작성") { _ ->
                // request server
                // TODO storeId -> 현재 선택되어 있는 상점의 storeId에 대한 viewmodel에서 가져오기
                // 또는 이 액티비티를 로드할 때 전 액티비티(지도)에서 데이터로 storeId를 받아서 사용하기
                val storeId = 10 // temp

                // 상세 후기 가져오기
                val content = binding.writeReviewContentEditText.text.toString()

                // 이미지 부분은 empty로 넘겨주고 추가 인자로 bitmap array 넘기기 (viewmodel에서 처리)
                val images = arrayListOf<String>()

                val data = WriteReviewData(
                    storeId,
                    content,
                    images,
                    flavorValue, sourValue, bitterValue,
                    aftertasteValue, zestValue, balanceValue
                )

                // 이미지 bitmap
                val bitmaps = arrayListOf<Bitmap>()

                if (imageUri1 != null) {
                    val iStream = this@WriteReviewActivity.contentResolver.openInputStream(imageUri1!!)
                    bitmaps.add(BitmapFactory.decodeStream(iStream))
                }
                if (imageUri2 != null) {
                    val iStream = this@WriteReviewActivity.contentResolver.openInputStream(imageUri2!!)
                    bitmaps.add(BitmapFactory.decodeStream(iStream))
                }
                if (imageUri3 != null) {
                    val iStream = this@WriteReviewActivity.contentResolver.openInputStream(imageUri3!!)
                    bitmaps.add(BitmapFactory.decodeStream(iStream))
                }

                // image는 따로 인자로 넣어주면 됨 (data에 넣기 x)
                viewModel.callPostWriteReviewAPI(data, bitmaps)
            }
            negativeButton(text = "취소")

            cancelOnTouchOutside(false)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        MaterialDialog(this).show {
            cornerRadius(16f)
            title(text = "주의")
            message(text = "나가시면 작성한 내용은 저장되지 않습니다. 메인 화면으로 가시겠습니까?")
            positiveButton(text = "확인") {
                finish()
            }
            negativeButton(text = "취소")
        }
    }
}