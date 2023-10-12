package com.example.android.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("author") val author: User,
    @SerializedName("message") val message: String,
    @SerializedName("created_at") val created_at: String
)
{
    data class User(
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("name") val name: String,
        @SerializedName("avatar_url") val avatar_url: String
    )
}
