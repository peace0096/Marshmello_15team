package com.konkuk.americano.UI.StoreDetail

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.americano.R

class DetailShopImagesRecyclerViewAdapter(
    private val items: ArrayList<Bitmap>
) : RecyclerView.Adapter<DetailShopImagesRecyclerViewAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(holder: ViewHolder, view: View, data: Bitmap, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.detailImageVIew)
        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageBitmap(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}