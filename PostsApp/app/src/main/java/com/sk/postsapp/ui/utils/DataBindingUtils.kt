package com.sk.postsapp.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingUtils {
    @JvmStatic
    @BindingAdapter(value = ["app:circleImageUrl"], requireAll = true)
    fun loadCircleImage(imageView: ImageView, circleImageUrl: String?) {
        if (circleImageUrl == null) return
        Glide.with(imageView.context).load(circleImageUrl).circleCrop().into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:imageUrl"], requireAll = true)
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl == null) return
        Glide.with(imageView.context).load(imageUrl).into(imageView)
    }
}