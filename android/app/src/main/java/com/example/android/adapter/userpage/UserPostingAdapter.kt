package com.example.android.adapter.userpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.databinding.UserPostingItemBinding
import com.example.android.retrofit.dto.UserPostingData

class UserPostingAdapter() : RecyclerView.Adapter<UserPostingAdapter.ViewHolder>() {
    lateinit var binding : UserPostingItemBinding
    private var userPostingList = ArrayList<UserPostingData>()

    fun setUserPosting(data: ArrayList<UserPostingData>?){
        if (data != null) {
            userPostingList = data
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
//        init{
//            binding.root.setOnClickListener(View.OnClickListener {
//                val pos = adapterPosition
//                //TODO 게시물 사진 클릭 시 게시물 상세 보기
//            })
//        }

        fun bind(position: Int, context: Context){
            Glide
                .with(context)
                .load(userPostingList[position].photo)
                .centerCrop()
                .into(binding.posting)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = UserPostingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, holder.itemView.context)
    }

    override fun getItemCount(): Int = userPostingList.size

}