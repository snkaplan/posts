package com.sk.postsapp.data.model

import com.google.gson.annotations.SerializedName

data class UpdatePostBody(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
)
