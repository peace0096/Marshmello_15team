package com.konkuk.americano.UI.StoreDetail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.konkuk.americano.Model.StoreReviewData
import com.konkuk.americano.R
import com.konkuk.americano.databinding.ActivityStoreDetailBinding
import com.konkuk.americano.Repo.StoreDetailRepo
import com.konkuk.americano.UI.WriteReview.WriteReviewActivity
import com.konkuk.americano.ViewModel.StoreDetailViewModel
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_store_detail.view.*
import java.util.*

class StoreDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityStoreDetailBinding

    lateinit var viewModel: StoreDetailViewModel

    private lateinit var imageAdapter: DetailShopImagesRecyclerViewAdapter
    private lateinit var reviewAdapter: ReviewsAdapter

    private val detailReview = arrayListOf<StoreReviewData>()

    private var nowEditFlag = 0 // 현재 편집 상태 0: 편집 불가 1: 편집 가능 2: 편집 중

    var imageBitmap1: Bitmap? = null
    var imageBitmap2: Bitmap? = null
    var imageBitmap3: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        initViewModel()

        initSwipeRefresh()

        // 본문 추가
        initObserver()

        // review recycler view review 추가
        initReviewList()

        // 작성 btn init
        initWriteBtn()

        checkMyReview()
    }

    private fun checkMyReview() {
        binding.detailStoreEditBtn.visibility = View.GONE
        binding.detailStoreContent.isEnabled = false

        binding.detailStoreEditBox.visibility = View.GONE
        // repo에 있는 현재 로그인된 유저 아이디와 로드된 글의 유저 아이디 비교

        binding.apply {
            detailStoreEditBtn.setOnClickListener {
                if (nowEditFlag == 0) {

                }
                else if (nowEditFlag == 1) {
                    nowEditFlag = 2
                    binding.detailStoreEditBtn.text = "저장"
                    binding.detailStoreContent.isEnabled = true // 편집 가능한 상태로

                    // frame layout 변경
                    storeImagesRecyclerView.visibility = View.GONE
                    detailStoreEditBox.visibility = View.VISIBLE
                    detailStoreEditBox.detailStoreEditTitle.setText(viewModel.storeDetailData.value!!.title)

                    // 작성 버튼 Gone
                    detailStoreWriteBtn.visibility = View.GONE

                    // 그외 안보이게 하기
                    detailStoreEvaulationLayout.visibility = View.GONE
                    detailStoreReviewsRecyclerView.visibility = View.GONE

                    // 이미지 선택
                    // 먼저 있는 이미지 보여주기
                    if (imageBitmap1 != null) {
                        detailStoreEditImages.addImage1.setImageBitmap(imageBitmap1)
                    }
                    if (imageBitmap2 != null) {
                        detailStoreEditImages.addImage2.setImageBitmap(imageBitmap2)
                    }
                    if (imageBitmap3 != null) {
                        detailStoreEditImages.addImage3.setImageBitmap(imageBitmap3)
                    }
                    detailStoreEditImages.addImage1.setImageBitmap(imageBitmap1)

                    // 갤러리 or 카메라로 이미지 선택 각각 버튼에 달아주기
                    detailStoreEditImages.imageAddLayoutPlusBtn.setOnClickListener {
                        TedImagePicker.with(this@StoreDetailActivity)
                            .startMultiImage {
                                    uriList -> setReviewImages(uriList)
                            }
                    }
                    detailStoreEditImages.addImage1.setOnClickListener {
                        TedImagePicker.with(this@StoreDetailActivity)
                            .start { uri ->
                                setReviewImage(uri, it)
                                imageBitmap1 = BitmapFactory.decodeStream(this@StoreDetailActivity.contentResolver.openInputStream(uri))
                            }
                    }
                    detailStoreEditImages.addImage2.setOnClickListener {
                        TedImagePicker.with(this@StoreDetailActivity)
                            .start { uri ->
                                setReviewImage(uri, it)
                                imageBitmap2 = BitmapFactory.decodeStream(this@StoreDetailActivity.contentResolver.openInputStream(uri))
                            }
                    }
                    detailStoreEditImages.addImage3.setOnClickListener {
                        TedImagePicker.with(this@StoreDetailActivity)
                            .start { uri ->
                                setReviewImage(uri, it)
                                imageBitmap3 = BitmapFactory.decodeStream(this@StoreDetailActivity.contentResolver.openInputStream(uri))
                            }
                    }

                } else {
                    // same as nowEditFlag == 2
                    val images = arrayListOf<Bitmap>()
                    if (imageBitmap1 != null) {
                        images.add(imageBitmap1!!)
                    }
                    if (imageBitmap2 != null) {
                        images.add(imageBitmap2!!)
                    }
                    if (imageBitmap3 != null) {
                        images.add(imageBitmap3!!)
                    }

                    // 편집 완료 후 저장 버튼 클릭 시 이벤트
                    viewModel.callEditStoreAPI(
                        binding.detailStoreEditBox.detailStoreEditTitle.text.toString(),
                        binding.detailStoreContent.text.toString(),
                        images
                    )

                    showDetails()
                    nowEditFlag = 1
                }
            }
        }
    }

    private fun setReviewImage(uri: Uri, view: View) {
        view as ImageView
        view.setImageURI(uri)
        view.background = null
    }

    private fun setReviewImages(uriList: List<Uri>) {
        // 3 장 초과 체크 했을 경우 -> 알림 메시지 띄우고 3개만 이미지 추가
        if (uriList.size > 3) {
            Toast.makeText(this, "이미지는 3장까지 선택가능합니다.", Toast.LENGTH_SHORT).show()
        }
        if (uriList.isEmpty()) {
            Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
            imageBitmap1 = null
            imageBitmap2 = null
            imageBitmap3 = null
            binding.apply {
                detailStoreEditImages.addImage1.background = getDrawable(R.drawable.image_select_box)
                detailStoreEditImages.addImage2.background = getDrawable(R.drawable.image_select_box)
                detailStoreEditImages.addImage3.background = getDrawable(R.drawable.image_select_box)
            }
        } else {
            binding.detailStoreEditImages.addImage1.apply {
                setImageURI(uriList[0])
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                background = null
            }
            //imageUri1 = uriList[0]
            imageBitmap1 = BitmapFactory.decodeStream(this@StoreDetailActivity.contentResolver.openInputStream(uriList[0]))
            imageBitmap2 = null
            imageBitmap3 = null

            if (uriList.size > 1) {
                binding.detailStoreEditImages.addImage2.apply {
                    setImageURI(uriList[1])
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    background = null
                }
                //imageUri2 = uriList[1]
                imageBitmap2 = BitmapFactory.decodeStream(this@StoreDetailActivity.contentResolver.openInputStream(uriList[1]))
            } else {
                binding.detailStoreEditImages.addImage2.setImageURI(null)
                binding.detailStoreEditImages.addImage2.background = getDrawable(R.drawable.image_select_box)
            }
            if (uriList.size > 2) {
                binding.detailStoreEditImages.addImage3.apply {
                    setImageURI(uriList[2])
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    background = null
                }
                //imageUri3 = uriList[2]
                imageBitmap3 = BitmapFactory.decodeStream(this@StoreDetailActivity.contentResolver.openInputStream(uriList[2]))
            } else {
                binding.detailStoreEditImages.addImage3.setImageURI(null)
                binding.detailStoreEditImages.addImage3.background = getDrawable(R.drawable.image_select_box)
            }
        }
    }

    private fun initWriteBtn() {
        binding.detailStoreWriteBtn.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initSwipeRefresh() {
        binding.detailStoreSwipe.setOnRefreshListener {
            Log.i("storeId", viewModel.selectedStoreId.value.toString())

            viewModel.setLiveData(StoreDetailRepo.getInstance().getModel()!!.storeId)

            viewModel.responseOk.observe(this, Observer {
                if (viewModel.responseOk.value == 3) {
                    Toast.makeText(this, "새로고침 성공", Toast.LENGTH_SHORT).show() // TODO temp
                    binding.detailStoreSwipe.isRefreshing = false
                }
            })
        }
    }

    private fun initViewModel() {
        // TODO 지도에서 선택된 매장 id로...
        val storeId = intent.getIntExtra("storeId", 1)
        viewModel = StoreDetailViewModel(this, this, storeId)
    }

    private fun initObserver() {
        viewModel.selectedStoreId.observe(this, Observer {
            viewModel.callGetStoreDetailAPI()
            viewModel.callGetStoreReviewsAPI()
        })
        viewModel.storeDetailData.observe(this, Observer {
            viewModel.callSetImages() // 매장 상세 정보가 load 완료됨 -> livedata 변경 감지 -> 이미지 가져오기
            showDetailOnScreen()
            viewModel.callGetUserMeAPI() // 유저 userId 가져오기 -> 후에 observe 되고 비교 후 매장 편집 기능 제공
        })
        viewModel.images.observe(this, Observer {
            // adapter내용 변경
            showStoreImages()
            if (it.size == 0) {
                return@Observer
            }
            if (it.size <= 1) {
                imageBitmap1 = if (it[0] != null) {
                    it[0]
                } else {
                    null
                }
            } else if (it.size <= 2) {
                imageBitmap1 = if (it[0] != null) {
                    it[0]
                } else {
                    null
                }
                imageBitmap2 = if (it[1] != null) {
                    it[1]
                } else {
                    null
                }
            } else if (it.size == 3) {
                imageBitmap1 = if (it[0] != null) {
                    it[0]
                } else {
                    null
                }
                imageBitmap2 = if (it[1] != null) {
                    it[1]
                } else {
                    null
                }
                imageBitmap3 = if (it[2] != null) {
                    it[2]
                } else {
                    null
                }
            }
        })
        viewModel.storeReviews.observe(this, Observer {
            showReviewsOnScreen()
        })
        viewModel.editResponseOk.observe(this, Observer {
            if (it == 1) {
                Toast.makeText(this, "매장 정보 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                val storeId = viewModel.selectedStoreId.value!!
                viewModel.setLiveData(storeId)
            } else if (it == -1) {
                Toast.makeText(this, "매장 정보 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getUserIdOk.observe(this, Observer {
            if (it == -1) {
                // fail to get userId
                // 실행 x
                Toast.makeText(this, "본인 정보 확인 실패", Toast.LENGTH_SHORT).show()
            } else {
                if (StoreDetailRepo.getInstance().getModel()?.userId == it) {
                    // 편집 버튼 보이게
                    binding.detailStoreEditBtn.visibility = View.VISIBLE
                    nowEditFlag = 1
                } else {
                    // 편집 버튼 안보이게 (기본적으로 안보이긴함)
                    binding.detailStoreEditBtn.visibility = View.GONE
                    nowEditFlag = 0
                }
            }
        })
    }

    private fun showStoreImages() {
        imageAdapter = DetailShopImagesRecyclerViewAdapter(viewModel.images.value!!)
        imageAdapter.itemClickListener =
            object : DetailShopImagesRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(
                    holder: DetailShopImagesRecyclerViewAdapter.ViewHolder,
                    view: View,
                    data: Bitmap,
                    position: Int
                ) {
                    // 사진 확대해서 보여주는 기능?
                }

            }
        binding.storeImagesRecyclerView.adapter = null
        binding.storeImagesRecyclerView.adapter = imageAdapter

        if (viewModel.images.value!!.size > 0) {
            if (viewModel.images.value!![0] != null) {
                imageBitmap1 = viewModel.images.value!![0]
            }
        } else if (viewModel.images.value!!.size > 1) {
            if (viewModel.images.value!![0] != null) {
                imageBitmap1 = viewModel.images.value!![0]
            }
            if (viewModel.images.value!![1] != null) {
                imageBitmap2 = viewModel.images.value!![1]
            }
        } else if (viewModel.images.value!!.size > 2) {
            if (viewModel.images.value!![0] != null) {
                imageBitmap1 = viewModel.images.value!![0]
            }
            if (viewModel.images.value!![1] != null) {
                imageBitmap2 = viewModel.images.value!![1]
            }
            if (viewModel.images.value!![2] != null) {
                imageBitmap3 = viewModel.images.value!![2]
            }
        }
    }

    private fun showReviewsOnScreen() {
        reviewAdapter = ReviewsAdapter(viewModel.storeReviews.value!!, this, this)
        reviewAdapter.itemClickListener =
            object : ReviewsAdapter.OnItemClickListener {
                override fun onItemClick(
                    holder: ReviewsAdapter.ViewHolder,
                    view: View,
                    data: StoreReviewData,
                    position: Int
                ) {
                    // 리뷰 확인인데 할 것이 있을까요
                }

            }
        binding.detailStoreReviewsRecyclerView.adapter = null
        binding.detailStoreReviewsRecyclerView.adapter = reviewAdapter
    }

    // 가져온 매장 상세정보를 화면에 출력
    private fun showDetailOnScreen() {
        if (viewModel.selectedStoreId != null) {
            // content
            binding.apply {
                detailStoreTitle.text = viewModel.storeDetailData.value!!.title
                detailStoreContent.setText(viewModel.storeDetailData.value!!.content)


                detailStoreEvaluation.reviewFlavorBar.progress = (viewModel.storeDetailData.value!!.flavor * 20).toInt()
                detailStoreEvaluation.reviewAftertasteBar.progress = (viewModel.storeDetailData.value!!.aftertaste * 20).toInt()
                detailStoreEvaluation.reviewSourBar.progress = (viewModel.storeDetailData.value!!.sour * 20).toInt()
                detailStoreEvaluation.reviewZestBar.progress = (viewModel.storeDetailData.value!!.zest * 20).toInt()
                detailStoreEvaluation.reviewBitterBar.progress = (viewModel.storeDetailData.value!!.bitter * 20).toInt()
                detailStoreEvaluation.reviewBalanceBar.progress = (viewModel.storeDetailData.value!!.balance * 20).toInt()

                detailStoreEvaluation.reviewFlavorValue.text = viewModel.storeDetailData.value!!.flavor.toString()
                detailStoreEvaluation.reviewAftertasteValue.text = viewModel.storeDetailData.value!!.aftertaste.toString()
                detailStoreEvaluation.reviewSourValue.text = viewModel.storeDetailData.value!!.sour.toString()
                detailStoreEvaluation.reviewZestValue.text = viewModel.storeDetailData.value!!.zest.toString()
                detailStoreEvaluation.reviewBitterValue.text = viewModel.storeDetailData.value!!.bitter.toString()
                detailStoreEvaluation.reviewBalanceValue.text = viewModel.storeDetailData.value!!.balance.toString()
            }
        } else {
            Toast.makeText(this, "현재 선택된 매장이 없습니다.", Toast.LENGTH_SHORT).show()
            // TODO 선택된 매장이 없을 경우 빈 내용을 보여야 하니 layout 수정할 것
        }
    }

    private fun initReviewList() {
        reviewAdapter = ReviewsAdapter(detailReview, this, this)
        reviewAdapter.itemClickListener =
            object : ReviewsAdapter.OnItemClickListener {
                override fun onItemClick(
                    holder: ReviewsAdapter.ViewHolder,
                    view: View,
                    data: StoreReviewData,
                    position: Int
                ) {

                }
            }
        binding.detailStoreReviewsRecyclerView.adapter = reviewAdapter
    }

    private fun showDetails() {
        binding.apply {
            detailStoreEditBtn.text = "편집"

            storeImagesRecyclerView.visibility = View.VISIBLE
            storeImagesRecyclerView.visibility = View.VISIBLE
            detailStoreEditBox.visibility = View.GONE

            detailStoreContent.isEnabled = false

            // 그외 안보이게 하기
            detailStoreEvaulationLayout.visibility = View.VISIBLE
            detailStoreReviewsRecyclerView.visibility = View.VISIBLE

            detailStoreWriteBtn.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if (nowEditFlag == 2) {
            MaterialDialog(this).show {
                cornerRadius(16f)
                title(text = "주의")
                message(text = "나가시면 작성한 내용은 저장되지 않습니다. 편집을 중단하고 나가시겠습니까?")
                positiveButton(text = "확인") {
                    nowEditFlag = 1
                    showDetails()
                }
                negativeButton(text = "취소")
            }
        }else {
            super.onBackPressed()
        }
    }
}