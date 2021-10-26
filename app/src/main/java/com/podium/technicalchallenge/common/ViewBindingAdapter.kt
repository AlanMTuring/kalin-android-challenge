package com.podium.technicalchallenge.common

import android.view.View
import androidx.databinding.BindingAdapter

class ViewBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("visibility", requireAll = true)
        fun setVisibility(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

}