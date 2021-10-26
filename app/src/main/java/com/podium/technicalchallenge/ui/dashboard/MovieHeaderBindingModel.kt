package com.podium.technicalchallenge.ui.dashboard

import dagger.Reusable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


data class MovieHeaderBindingModel(val title: String,
                                   val releaseDate: String,
                                   val duration: String,
                                   val imageUrl: String?)

@Reusable
class MovieHeaderBindingModelFactory @Inject constructor() {
    fun create(movie: MovieHeaderModel): MovieHeaderBindingModel {
        return MovieHeaderBindingModel(movie.title, formatDate(movie.releaseDate), formatDuration(movie.runtime), movie.imageUrl)
    }

    fun formatDate(releaseDate: String): String {
        val date = LocalDate.parse(releaseDate)
        val format = DateTimeFormatter.ofPattern("MMM d, yyyy")
        return date.format(format)
    }

    fun formatDuration(runtime: Int): String {
        return "${runtime}m"
    }
}