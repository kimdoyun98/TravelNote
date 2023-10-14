package com.example.android.retrofit.dto

import com.google.gson.annotations.SerializedName

data class SearchUser(
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatar_url: String,
)
