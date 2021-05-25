package com.konkuk.americano.ui.storedetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konkuk.americano.R
import com.konkuk.americano.databinding.FragmentStoreDetailBinding
import com.konkuk.americano.ui.storedetail.DetailShopImagesRecyclerViewAdapter
import com.konkuk.americano.ui.storedetail.ReviewsAdapter
import com.konkuk.americano.util.Evaluations
import com.konkuk.americano.util.Review

/*
* Fragment 생명 주기
* onAttach -> onCreate -> onCreateView -> onActivityCreated -> onStart -> onResume
* ACTIVE
* onPause -> onStop -> onDestroyView -> onDetach
* DESTROYED
* */
class StoreDetailFragment : Fragment() {
    private lateinit var binding: FragmentStoreDetailBinding
    lateinit var imageAdapter: DetailShopImagesRecyclerViewAdapter
    lateinit var reviewAdapter: ReviewsAdapter

    private val detailImages = arrayListOf<Bitmap>()
    private val detailReview = arrayListOf<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        // 이미지 서버로 부터 가져오기 (image: Bitmap)
        initTestImages()

        // 본문 추가
        initStoreDetailContent()

        // 후기 내용 불러오기
        initTestReview()

        // review recycler view review 추가
        initReviewList()
    }

    private fun initReviewList() {
        reviewAdapter = ReviewsAdapter(detailReview)
        reviewAdapter.itemClickListener =
            object : ReviewsAdapter.OnItemClickListener {
                override fun onItemClick(
                    holder: ReviewsAdapter.ViewHolder,
                    view: View,
                    data: Review,
                    position: Int
                ) {

                }
            }
        binding.reviewsRecyclerview.adapter = reviewAdapter
    }

    private fun initTestImages() {
        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe1))
        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe2))
        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe3))
        detailImages.add(BitmapFactory.decodeResource(resources, R.drawable.sample_cafe1))

        // image recycler view 에 이미지 추가
        imageAdapter = DetailShopImagesRecyclerViewAdapter(detailImages)
        imageAdapter.itemClickListener =
            object : DetailShopImagesRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(
                    holder: DetailShopImagesRecyclerViewAdapter.ViewHolder,
                    view: View,
                    data: Bitmap,
                    position: Int
                ) {

                }

            }
        binding.shopImagesRecyclerview.adapter = imageAdapter
    }

    private fun initTestReview() {
        // temp - 서버로 부터 값 가져오기
        // 맛 평가 값
        val tempMap: MutableMap<Evaluations, Int> = mutableMapOf(
            Evaluations.FLAVOUR to 4,
            Evaluations.AFTERTASTE to 1,
            Evaluations.SOUR to 2,
            Evaluations.ZEST to 0,
            Evaluations.BITTER to 3,
            Evaluations.BALANCE to 5
        )

        // 매장 이미지 3장
        val images = arrayListOf<Bitmap>(
            BitmapFactory.decodeResource(resources, R.drawable.sample_cafe1),
            BitmapFactory.decodeResource(resources, R.drawable.sample_cafe2),
            BitmapFactory.decodeResource(resources, R.drawable.sample_cafe3)
        )
        // 유저 아이콘
        val userIcon = BitmapFactory.decodeResource(resources, R.drawable.profile_icon)

        // 모든 리뷰들
        detailReview.add(Review(
            "이디야",
            "가격 양호",
            tempMap,
            images,
            userIcon,
            "TEST NICK",
            "2021-00-00"))
        detailReview.add(Review(
            "스타벅스",
            "가격 양호",
            tempMap,
            images,
            userIcon,
            "TEST NICK",
            "2021-00-00"))
        detailReview.add(Review(
            "엔젤인어스",
            "가격 양호",
            tempMap,
            images,
            userIcon,
            "TEST NICK",
            "2021-00-00"))
    }

    private fun initStoreDetailContent() {
        // 매장 정보 가져오기
        binding.detailshopContent.text = "테스트"
    }
}