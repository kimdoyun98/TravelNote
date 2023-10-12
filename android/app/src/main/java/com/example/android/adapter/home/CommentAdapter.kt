package com.example.android.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.databinding.CommentItemBinding
import com.example.android.retrofit.dto.Comment

class CommentAdapter(private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var commentArray = ArrayList<Comment>()
    lateinit var binding : CommentItemBinding

    fun setComment(comment: ArrayList<Comment>){
        Log.e("Adpater comment", comment.toString())
        commentArray = comment
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(pos : Int){
            Log.e("commentArray", commentArray[pos].message)
            binding.commentData = commentArray[pos]

            Glide
                .with(itemView.context)
                .load(commentArray[pos].author.avatar_url)
                .centerCrop()
                .into(binding.userProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            lifecycleOwner = this@CommentAdapter.lifecycleOwner
        }
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return commentArray.size
    }
}