package com.example.android.adapter.search_user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.Secret
import com.example.android.databinding.SearchUserItemBinding
import com.example.android.retrofit.dto.UserData

class SearchUserAdapter() : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {
    lateinit var binding : SearchUserItemBinding
    private lateinit var searchUserClickEvent : SearchUserClickEvent
    private var userList = ArrayList<UserData>()

    fun setUserList(_userList : ArrayList<UserData>){
        userList = _userList

        notifyDataSetChanged()
    }

    fun setSearchUserClickEvent(s : SearchUserClickEvent){
        searchUserClickEvent = s
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init{
            binding.root.setOnClickListener(View.OnClickListener {
                val pos = adapterPosition
                searchUserClickEvent.searchUserClick(userList[pos])
            }

            )
        }

        fun bind(position: Int, context: Context){
            binding.username.text = userList[position].username

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