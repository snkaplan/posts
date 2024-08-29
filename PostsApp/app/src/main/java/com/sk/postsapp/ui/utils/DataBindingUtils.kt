package com.sk.postsapp.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingUtils {
    @JvmStatic
    @BindingAdapter(value = ["app:imageUrl"], requireAll = true)
    fun loadCircleImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl == null) return
        Glide.with(imageView.context).load(imageUrl).circleCrop().into(imageView)
    }
}