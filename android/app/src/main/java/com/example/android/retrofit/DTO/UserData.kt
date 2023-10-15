package com.example.android.retrofit.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData(
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatar_url: String,
    @SerializedName("follower_set") val follower_set: ArrayList<String>,
    @SerializedName("following_set") val following_set: ArrayList<String>
):Serializable
