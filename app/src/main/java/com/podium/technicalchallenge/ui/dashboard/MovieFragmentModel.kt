package com.podium.technicalchallenge.ui.dashboard

import java.util.*
import javax.inject.Inject


data class MovieFragmentModel(val isLoading: Boolean,
                              val isError: Boolean,
                              val movieList: List<MovieHeaderModel>)

data class MovieHeaderModel(val id: Int,
                            val title: String,
                            val runtime: Int,
                            val releaseDate: String,
                            val popularity: Double,
                            val imageUrl: String?)

class MovieFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieFragmentModel(isLoading = true, isError = false, movieList = listOf())
    fun updateModelWithMovieHeaders(previousModel: MovieFragmentModel, movies: List<MovieHeaderModel>) = previousModel.copy(movieList = movies, isLoading = false)
    fun updateModelWithError(previousModel: MovieFragmentModel)  = previousModel.copy(isError = true, isLoading = false)
}