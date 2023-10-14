package com.example.android.adapter.search_user

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.Secret
import com.example.android.databinding.SearchUserItemBinding
import com.example.android.retrofit.dto.SearchUser

class SearchUserAdapter(private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {
    lateinit var binding : SearchUserItemBinding
    private var userList = ArrayList<SearchUser>()

    fun setUserList(_userList : ArrayList<SearchUser>){
        userList = _userList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int, context: Context){
            binding.username.text = userList[position].username

            Log.e("avatar_url", userList[position].avatar_url)
            Glide
                .with(context)
                .load("${Secret.BaseUrl}${userList[position].avatar_url}")
                .centerCrop()
                .into(binding.userProfile)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = SearchUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, holder.itemView.context)
    }

    override fun getItemCount(): Int = userList.size

}