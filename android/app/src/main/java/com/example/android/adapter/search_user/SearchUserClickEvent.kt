package com.example.android.adapter.search_user

import com.example.android.retrofit.dto.UserData

interface SearchUserClickEvent {
    fun searchUserClick(userdata: UserData)
}