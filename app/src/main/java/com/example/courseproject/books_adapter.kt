package com.example.courseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class books_adapter(private val books: List<String>) :
    RecyclerView.Adapter<books_adapter.MyViewHolder1>() {

    //private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    //fun setOnItemClickListener(listener: onItemClickListener){
    //    mListener = listener
    //}

    class MyViewHolder1 (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val book_text: TextView = itemView.findViewById(R.id.book_name)

        //init {
        //    itemView.setOnClickListener {
        //        listener.onItemClick(adapterPosition)
        //    }
        //}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item, parent, false)
        return MyViewHolder1(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        holder.book_text.text = books[position]
    }

    override fun getItemCount() = books.size
}