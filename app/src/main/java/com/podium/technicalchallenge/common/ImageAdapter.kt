package com.podium.technicalchallenge.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class ImageAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrBlank()) {
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    //This is just for sake of speed. in reality i would grab and download or create a default image and not use an image from a random website
                    .load("https://www.realbacking.com/wp-content/themes/decibel/images/woocommerce/placeholder.jpg")
                    .into(imageView)
            }
        }
    }
}