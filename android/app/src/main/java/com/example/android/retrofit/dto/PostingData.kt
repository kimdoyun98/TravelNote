package com.example.android.retrofit.dto

import com.google.gson.annotations.SerializedName

data class PostingData(
    @SerializedName("id") val id: Int,
    @SerializedName("author") val author: User,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("location") val location: String,
    @SerializedName("tag_set") val tag_set: ArrayList<Any>,
    @SerializedName("like_user_set") val like_user_set: ArrayList<Any>
    )
{
    data class User(
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("name") val name: String,
        @SerializedName("avatar_url") val avatar_url: String
    )
}
