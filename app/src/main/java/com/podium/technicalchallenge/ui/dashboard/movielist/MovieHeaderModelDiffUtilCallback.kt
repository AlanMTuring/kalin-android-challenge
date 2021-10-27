package com.podium.technicalchallenge.ui.dashboard.movielist

import androidx.recyclerview.widget.DiffUtil
import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel


class MovieHeaderModelDiffUtilCallback : DiffUtil.ItemCallback<MovieHeaderModel>() {

    companion object {
        const val TITLE_KEY = "TITLE"
        const val IMAGE_URL_KEY = "IMAGE_URL"
        const val RELEASE_DATE_KEY = "RELEASE_DATE"
        const val RUNTIME_KEY = "RUNTIME"
    }

    override fun areItemsTheSame(oldItem: MovieHeaderModel, newItem: MovieHeaderModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieHeaderModel, newItem: MovieHeaderModel): Boolean {
        return oldItem.title == newItem.title
                && oldItem.imageUrl == newItem.imageUrl
                && oldItem.releaseDate == newItem.releaseDate
                && oldItem.runtime == newItem.runtime
    }

    override fun getChangePayload(oldItem: MovieHeaderModel, newItem: MovieHeaderModel): Any? {
        val payload = mutableMapOf<String, Any?>()

        if (oldItem.title != newItem.title) {
            payload[TITLE_KEY] = newItem.title
        }
        if (oldItem.imageUrl != newItem.imageUrl) {
            payload[IMAGE_URL_KEY] = newItem.imageUrl
        }
        if (oldItem.releaseDate != newItem.releaseDate) {
            payload[RELEASE_DATE_KEY] = newItem.releaseDate
        }
        if (oldItem.runtime != newItem.runtime) {
            payload[RUNTIME_KEY] = newItem.runtime
        }

        return payload
    }
}