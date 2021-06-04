package com.konkuk.americano.ui.storedetail

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.konkuk.americano.databinding.ActivityStoreDetailBinding
import com.konkuk.americano.model.StoreReviewData
import com.konkuk.americano.viewmodel.StoreDetailViewModel

class StoreDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityStoreDetailBinding

    lateinit var viewModel: StoreDetailViewModel

    lateinit var imageAdapter: DetailShopImagesRecyclerViewAdapter
    lateinit var reviewAdapter: ReviewsAdapter

    private val detailReview = arrayListOf<StoreReviewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        initViewModel()

        initSwipeRefresh()

        // 이미지 서버로 부터 가져오기 (image: Bitmap)
        initTestImages()

        // 본문 추가
        initObserver()

        // 후기 내용 불러오기
        initTestReview()

        // review recycler view review 추가
        initReviewList()
    }

    private fun initSwipeRefresh() {
        binding.detailStoreSwipe.setOnRefreshListener {
            //TODO 마찬가지로 storeId 지도 액티비티에서 가져오든가 해야함 임시로 3
            viewModel.setLiveData(10)
            Log.i("storeId", viewModel.selectedStoreId.value.toString())

            viewModel.responseOk.observe(this, Observer {
                if (viewModel.responseOk.value == 3) {
                    Toast.makeText(this, "새로고침 성공", Toast.LENGTH_SHORT).show() // TODO temp
                    binding.detailStoreSwipe.isRefreshing = false
                }
            })
        }
    }


    private fun initViewModel() {
        viewModel = StoreDetailViewModel(this, this)
    }

    private fun initObserver() {
        //val storeId = 1 // temp 지도에서 선택되면 변경하게 할 것

        viewModel.selectedStoreId.observe(this, Observer {
            viewModel.callGetStoreDetailAPI()
            viewModel.callGetStoreReviewsAPI()
        })
        viewModel.storeDetailData.observe(this, Observer {
            viewModel.callSetImages() // 매장 상세 정보가 load 완료됨 -> livedata 변경 감지 -> 이미지 가져오기
            showDetailOnScreen()
        })
        viewModel.images.observe(this, Observer {
            // adapter내용 변경
            showStoreImages()
        })
        viewModel.storeReviews.observe(this, Observer {
            showReviewsOnScreen()
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
                detailStoreContent.text = viewModel.storeDetailData.value!!.content


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


    private fun initTestReview() {
        // temp - 서버로 부터 값 가져오기
        // 맛 평가 값
//        val tempMap: MutableMap<Evaluations, Int> = mutableMapOf(
//            Evaluations.FLAVOUR to 4,
//            Evaluations.AFTERTASTE to 1,
//            Evaluations.SOUR to 2,
//            Evaluations.ZEST to 0,
//            Evaluations.BITTER to 3,
//            Evaluations.BALANCE to 5
//        )

        // 매장 이미지 3장
//        val images = arrayListOf<Bitmap>(
//            BitmapFactory.decodeResource(resources, R.drawable.sample_cafe1),
//            BitmapFactory.decodeResource(resources, R.drawable.sample_cafe2),
//            BitmapFactory.decodeResource(resources, R.drawable.sample_cafe3)
//        )
        // 유저 아이콘
        //val userIcon = BitmapFactory.decodeResource(resources, R.drawable.profile_icon)

        // 모든 리뷰들
        detailReview.add(
            StoreReviewData(
                1,
                -1,
                "가격 양호",
                arrayListOf<String>(),
                "2021-00-00",
                1, 2, 3, 4, 5, 6,
                "nick3",
                arrayListOf<String>()
            )
        )
        detailReview.add(
            StoreReviewData(
                1,
                -1,
                "가격 양호",
                arrayListOf<String>(),
                "2021-00-00",
                1, 2, 3, 4, 5, 6,
                "nick2",
                arrayListOf<String>()
            )

        )
        detailReview.add(
            StoreReviewData(
                1,
                -1,
                "가격 양호",
                arrayListOf<String>(),
                "2021-00-00",
                1, 2, 3, 4, 5, 6,
                "nick1",
                arrayListOf<String>()
            )
        )
    }

    private fun initTestImages() {
//        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe1))
//        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe2))
//        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe3))
//        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe1))
//
//        // image recycler view 에 이미지 추가
//        imageAdapter = DetailShopImagesRecyclerViewAdapter(detailImages)
//        imageAdapter.itemClickListener =
//            object : DetailShopImagesRecyclerViewAdapter.OnItemClickListener {
//                override fun onItemClick(
//                    holder: DetailShopImagesRecyclerViewAdapter.ViewHolder,
//                    view: View,
//                    data: Bitmap,
//                    position: Int
//                ) {
//
//                }
//
//            }
//        binding.storeImagesRecyclerView.adapter = imageAdapter
    }
}