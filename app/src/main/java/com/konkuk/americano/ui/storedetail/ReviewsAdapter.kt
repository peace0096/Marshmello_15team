package com.konkuk.americano.ui.storedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.americano.R
import com.konkuk.americano.databinding.ReviewRowBinding
import com.konkuk.americano.util.Evaluations
import com.konkuk.americano.util.Review

class ReviewsAdapter(
    private val items: ArrayList<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(holder: ViewHolder, view: View, data: Review, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // binding 으로 묶는 방법으로 바꾸기...
//        val title = itemView.findViewById<TextView>(R.id.review_row_title)
//        val content = itemView.findViewById<TextView>(R.id.review_row_content)
//        val flavour_bar = itemView.findViewById<ProgressBar>(R.id.review_flavour_bar)
//        val flavour_value = itemView.findViewById<TextView>(R.id.review_flavour_value)
//        val aftertaste_bar = itemView.findViewById<ProgressBar>(R.id.review_aftertaste_bar)
//        val aftertaste_value = itemView.findViewById<TextView>(R.id.review_aftertaste_value)
//        val sour_bar = itemView.findViewById<ProgressBar>(R.id.review_sour_bar)
//        val sour_value = itemView.findViewById<TextView>(R.id.review_sour_value)
//        val zest_bar = itemView.findViewById<ProgressBar>(R.id.review_zest_bar)
//        val zest_value = itemView.findViewById<TextView>(R.id.review_zest_value)
//        val bitter_bar = itemView.findViewById<ProgressBar>(R.id.review_bitter_bar)
//        val bitter_value = itemView.findViewById<TextView>(R.id.review_bitter_value)
//        val balance_bar = itemView.findViewById<ProgressBar>(R.id.review_balance_bar)
//        val balance_value = itemView.findViewById<TextView>(R.id.review_balance_value)
//        val image1 = itemView.findViewById<ImageView>(R.id.review_image1)
//        val image2 = itemView.findViewById<ImageView>(R.id.review_image2)
//        val image3 = itemView.findViewById<ImageView>(R.id.review_image3)
//        val user_icon = itemView.findViewById<ImageView>(R.id.review_usericon)
//        val user_nick = itemView.findViewById<TextView>(R.id.review_usernick)
//        val date = itemView.findViewById<TextView>(R.id.review_date)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    lateinit var binding: ReviewRowBinding
    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ReviewRowBinding.bind(LayoutInflater.from(parent.context)
            .inflate(R.layout.review_row, parent, false))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // 값이 존재하지 않으면(에러) 0 값으로 대체체
        val flavour = item.statistics.get(Evaluations.FLAVOUR)?:0
        val aftertaste = item.statistics.get(Evaluations.AFTERTASTE)?:0
        val sour = item.statistics.get(Evaluations.SOUR)?:0
        val zest = item.statistics.get(Evaluations.ZEST)?:0
        val bitter = item.statistics.get(Evaluations.BITTER)?:0
        val balance = item.statistics.get(Evaluations.BALANCE)?:0

        binding.reviewRowTitle.text = item.shopName
        binding.reviewRowContent.text = item.detailContent

        binding.reviewFlavourBar.progress = flavour
        binding.reviewFlavourValue.text = flavour.toString()
        binding.reviewAftertasteBar.progress = aftertaste * 20
        binding.reviewAftertasteValue.text = aftertaste.toString()
        binding.reviewSourBar.progress = sour * 20
        binding.reviewSourValue.text = sour.toString()
        binding.reviewZestBar.progress = zest * 20
        binding.reviewZestValue.text = zest.toString()
        binding.reviewBitterBar.progress = bitter * 20
        binding.reviewBitterValue.text = bitter.toString()
        binding.reviewBalanceBar.progress = balance * 20
        binding.reviewBalanceValue.text = balance.toString()

        binding.reviewImage1.setImageBitmap(item.images[0])
        binding.reviewImage2.setImageBitmap(item.images[1])
        binding.reviewImage3.setImageBitmap(item.images[2])

        binding.reviewUsericon.setImageBitmap(item.userIcon)
        binding.reviewUsernick.text = item.userNick
        binding.reviewDate.text = item.date
    }

    override fun getItemCount(): Int {
        return items.size
    }
}