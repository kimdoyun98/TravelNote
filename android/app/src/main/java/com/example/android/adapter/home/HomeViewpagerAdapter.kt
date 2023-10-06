package com.example.android.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R

class HomeViewpagerAdapter(private val path : String) :
    RecyclerView.Adapter<HomeViewpagerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val previewImage : ImageView = itemView.findViewById(R.id.homeImage)
        fun bind(path: String){
            Glide
                .with(itemView.context)
                .load(path)
                .centerCrop()
                .into(previewImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_viewpager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(path)
    }

    override fun getItemCount(): Int {
        return 1
    }

}