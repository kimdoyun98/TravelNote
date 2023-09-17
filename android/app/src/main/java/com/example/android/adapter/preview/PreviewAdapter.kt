package com.example.android.adapter.preview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R

class PreviewAdapter(val paths : ArrayList<String>):
    RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {
    inner class PreviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val previewImage : ImageView = itemView.findViewById(R.id.previewImage)
        fun bind(path: String){
            Glide
                .with(itemView.context)
                .load(path)
                .centerCrop()
                .into(previewImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        return PreviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.preview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.bind(paths[position])
    }

    override fun getItemCount(): Int {
        return paths.size
    }

}