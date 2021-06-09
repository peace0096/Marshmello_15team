package com.konkuk.americano.UI.MyReview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.americano.Model.Review_Model
import com.konkuk.americano.UI.MyStore.MyStoreRecyclerAdapter
import com.konkuk.americano.databinding.MyReviewRowCellBinding
import com.konkuk.americano.databinding.MyStoreListCellBinding


class MyReviewAdapter(var data: LiveData<ArrayList<Review_Model>>) : RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {


    inner class ViewHolder(val binding : MyReviewRowCellBinding) : RecyclerView.ViewHolder(binding.root){


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyReviewAdapter.ViewHolder {
        val view = MyReviewRowCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyReviewAdapter.ViewHolder, position: Int) {
        val item = data.value!![position]

        // 값이 존재하지 않으면(에러) 0 값으로 대체
        val flavor = item.flavor
        val aftertaste = item.aftertaste
        val sour = item.sour
        val zest = item.zest
        val bitter = item.bitter
        val balance = item.balance


        holder.binding.myReviewRowContent.text = item.content


        holder.binding.reviewEvaluation.reviewFlavorBar.progress = (flavor * 20).toInt()
        holder.binding.reviewEvaluation.reviewFlavorValue.text = flavor.toString()
        holder.binding.reviewEvaluation.reviewAftertasteBar.progress = (aftertaste * 20).toInt()
        holder.binding.reviewEvaluation.reviewAftertasteValue.text = aftertaste.toString()
        holder.binding.reviewEvaluation.reviewSourBar.progress = (sour * 20).toInt()
        holder.binding.reviewEvaluation.reviewSourValue.text = sour.toString()
        holder.binding.reviewEvaluation.reviewZestBar.progress = (zest * 20).toInt()
        holder.binding.reviewEvaluation.reviewZestValue.text = zest.toString()
        holder.binding.reviewEvaluation.reviewBitterBar.progress = (bitter * 20).toInt()
        holder.binding.reviewEvaluation.reviewBitterValue.text = bitter.toString()
        holder.binding.reviewEvaluation.reviewBalanceBar.progress = (balance * 20).toInt()
        holder.binding.reviewEvaluation.reviewBalanceValue.text = balance.toString()

        if (item.image.size == 0 ){
            holder.binding.myReviewImage1.visibility = View.GONE
            holder.binding.myReviewImage2.visibility = View.GONE
            holder.binding.myReviewImage3.visibility = View.GONE
            holder.binding.myReviewRowImagesLayout.visibility = View.GONE
        }
        else {
            holder.binding.myReviewImage1.visibility = View.VISIBLE
            holder.binding.myReviewImage2.visibility = View.VISIBLE
            holder.binding.myReviewImage3.visibility = View.VISIBLE
            holder.binding.myReviewRowImagesLayout.visibility = View.VISIBLE
        }


        for (i in 0 until item.image.size) {
            when (i) {
                1 ->  {
                    Glide.with(holder.binding.root)
                        .load(item.image[0])
                        .into(holder.binding.myReviewImage1)

                }
                2 ->  {
                    Glide.with(holder.binding.root)
                        .load(item.image[1])
                        .into(holder.binding.myReviewImage2)

                }
                3 ->  {
                    Glide.with(holder.binding.root)
                        .load(item.image[2])
                        .into(holder.binding.myReviewImage3)

                }
            }
        }

        if (item.profileImage.size != 0) {
            Glide.with(holder.binding.root)
                .load(item.profileImage[0])
                .into(holder.binding.myReviewUsericon)
        }
        holder.binding.myReviewUsernick.text = item.nickname
        holder.binding.myReviewDate.text = item.createdAt.split(" ")[0]




    }

    override fun getItemCount(): Int {
        return data.value!!.size

    }
    //

}