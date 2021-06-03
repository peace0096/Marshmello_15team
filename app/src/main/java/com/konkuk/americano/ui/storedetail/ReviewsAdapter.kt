package com.konkuk.americano.ui.storedetail

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.americano.R
import com.konkuk.americano.databinding.ReviewRowBinding
import com.konkuk.americano.model.StoreReviewData
import com.konkuk.americano.util.Evaluations
import com.konkuk.americano.viewmodel.ReviewsViewModel

class ReviewsAdapter(
    private val items: ArrayList<StoreReviewData>,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>(){
    private lateinit var binding: ReviewRowBinding
    val viewModel = ReviewsViewModel()

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(holder: ViewHolder, view: View, data: StoreReviewData, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ReviewRowBinding.bind(LayoutInflater.from(parent.context)
            .inflate(R.layout.review_row, parent, false))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // 값이 존재하지 않으면(에러) 0 값으로 대체
        val flavor = item.flavor
        val aftertaste = item.aftertaste
        val sour = item.sour
        val zest = item.zest
        val bitter = item.bitter
        val balance = item.balance

        binding.reviewRowContent.text = item.content

        binding.reviewEvaluation.reviewFlavorBar.progress = flavor
        binding.reviewEvaluation.reviewFlavorValue.text = flavor.toString()
        binding.reviewEvaluation.reviewAftertasteBar.progress = aftertaste * 20
        binding.reviewEvaluation.reviewAftertasteValue.text = aftertaste.toString()
        binding.reviewEvaluation.reviewSourBar.progress = sour * 20
        binding.reviewEvaluation.reviewSourValue.text = sour.toString()
        binding.reviewEvaluation.reviewZestBar.progress = zest * 20
        binding.reviewEvaluation.reviewZestValue.text = zest.toString()
        binding.reviewEvaluation.reviewBitterBar.progress = bitter * 20
        binding.reviewEvaluation.reviewBitterValue.text = bitter.toString()
        binding.reviewEvaluation.reviewBalanceBar.progress = balance * 20
        binding.reviewEvaluation.reviewBalanceValue.text = balance.toString()

        // 매장 이미지 가져오기
        viewModel.loadStoreImages(context, item.image)
        viewModel.storeImages.observe(lifecycleOwner, Observer {
            val size = viewModel.storeImages.value!!.size
            if (size == 1) {
                binding.reviewImage1.setImageBitmap(viewModel.storeImages.value!![0])
            } else if (size == 2) {
                binding.reviewImage1.setImageBitmap(viewModel.storeImages.value!![0])
                binding.reviewImage2.setImageBitmap(viewModel.storeImages.value!![1])
            } else {
                binding.reviewImage1.setImageBitmap(viewModel.storeImages.value!![0])
                binding.reviewImage2.setImageBitmap(viewModel.storeImages.value!![1])
                binding.reviewImage3.setImageBitmap(viewModel.storeImages.value!![2])
            }
        })

        // profile image
        // todo lifecyclerowner 여기 가져와서 써도 되나...?
        viewModel.loadProfileImage(context, item.profileImage[0])
        viewModel.profileImage.observe(lifecycleOwner, Observer {
            binding.reviewUsericon.setImageBitmap(viewModel.profileImage.value)
        })

        // nickname
        binding.reviewUsernick.text = item.nickname

        // 날짜 표시 yyyy-MM-dd 만 표시 (뒤에 시 분 초 생략)
        // TODO 일단 createdAt이 서버에서 안줌... -> ? 사용 일단
        Log.i("ITEM", item.toString())
        if (item.createdAt != null) {
            binding.reviewDate.text = item.createdAt.split(" ")[0]
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}