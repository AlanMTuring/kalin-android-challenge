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
        val date = LocalDate.parse(movie.releaseDate)
        val format = DateTimeFormatter.ofPattern("MMM d, yyyy")
        val formattedDate = date.format(format)
        return MovieHeaderBindingModel(movie.title, formattedDate, "${movie.runtime}m", movie.imageUrl)
    }
}