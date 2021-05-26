package com.konkuk.americano.MyStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.americano.databinding.ActivityMyStoreBinding
import com.konkuk.americano.databinding.MyStoreListCellBinding

class MyStoreRecyclerAdapter(var data: ArrayList<MyStoreData>)  : RecyclerView.Adapter<MyStoreRecyclerAdapter.ViewHolder>() {


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
           myStoreListStoreCreatedAt.text = "생성일 : " + data[position].createdAt
           myStoreListStoreTitle.text = "매장이름 : "  + data[position].title

       }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}