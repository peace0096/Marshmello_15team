package com.konkuk.americano.UI.MyStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.americano.Model.Store_Model
import com.konkuk.americano.databinding.MyStoreListCellBinding

class MyStoreRecyclerAdapter(var data: LiveData<ArrayList<Store_Model>>)  : RecyclerView.Adapter<MyStoreRecyclerAdapter.ViewHolder>() {


    interface onItemClickListener {
        fun onItemClick(view : View, position: Int)

    }

    var itemClickListener : onItemClickListener? = null


    inner class ViewHolder(val binding : MyStoreListCellBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemClickListener!!.onItemClick(it, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MyStoreListCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding.apply {
           data.value!!.get(position).let { item ->
               with(holder) {
                   myStoreListStoreCreatedAt.text = "생성일 : " + item.createdAt
                   myStoreListStoreTitle.text = "매장이름 : "  + item.title
                   Glide.with(holder.binding.root)
                       .load(item.image.get(0))
                       .into(myStoreListStoreImage)
                   myStoreListStoreImage.scaleType = ImageView.ScaleType.CENTER_CROP
               }
           }
       }
    }

    override fun getItemCount(): Int {
        return data.value!!.size
    }

}