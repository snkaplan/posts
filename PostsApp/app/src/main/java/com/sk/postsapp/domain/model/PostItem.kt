package com.sk.postsapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostItem(val id: Int, val userId: Int, val title: String, val body: String, val image: String) : Parcelable
