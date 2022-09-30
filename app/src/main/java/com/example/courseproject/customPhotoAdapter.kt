package com.example.courseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.app.Activity
import android.content.Context
import android.widget.ImageView

class customPhotoAdapter (private val context: Context, private val images: List<String>) :
    RecyclerView.Adapter<customPhotoAdapter.MyViewHolder1>() {

    //private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    //fun setOnItemClickListener(listener: onItemClickListener){
    //    mListener = listener
    //}

    class MyViewHolder1 (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.item_photo_view)

        //init {
        //    itemView.setOnClickListener {
        //        listener.onItemClick(adapterPosition)
        //    }
        //}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_item, parent, false)
        return MyViewHolder1(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        //holder.book_text.text = books[position]
        val imageId = images[position]
        Picasso.with(context)
            .load("https://covers.openlibrary.org/a/id/$imageId.jpg")
            .placeholder(androidx.appcompat.R.drawable.abc_btn_colored_material)
            .error(R.drawable.ic_launcher_background)
            .into(holder.photo)
    }

    override fun getItemCount() = images.size
}