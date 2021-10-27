package com.podium.technicalchallenge.ui.dashboard

import dagger.Reusable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


data class MovieHeaderBindingModel(val title: String,
                                   val formattedReleaseDate: String,
                                   val releaseDate: LocalDate,
                                   val formattedRuntime: String,
                                   val imageUrl: String?)

@Reusable
class MovieHeaderBindingModelFactory @Inject constructor() {
    fun create(movie: MovieHeaderModel): MovieHeaderBindingModel {
        return MovieHeaderBindingModel(movie.title, formatDate(movie.releaseDate), LocalDate.parse(movie.releaseDate), formatDuration(movie.runtime), movie.imageUrl)
    }

    fun formatDate(releaseDate: String): String {
        val date = LocalDate.parse(releaseDate)
        val format = DateTimeFormatter.ofPattern("MMM d, yyyy")
        return date.format(format)
    }

    fun formatDuration(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "${hours}h ${minutes}m"
    }
}