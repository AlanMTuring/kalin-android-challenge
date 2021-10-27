package com.podium.technicalchallenge.ui.dashboard

import javax.inject.Inject

data class MovieFragmentModel(val isLoading: Boolean,
                              val isError: Boolean,
                              val movieList: List<MovieHeaderModel>)

data class MovieHeaderModel(val id: Int = 0,
                            val title: String = "",
                            val runtime: Int = 0,
                            val releaseDate: String = "",
                            val popularity: Double = 0.0,
                            val imageUrl: String? = null)

class MovieFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieFragmentModel(isLoading = true, isError = false, movieList = listOf())
    fun updateModelWithMovieHeaders(previousModel: MovieFragmentModel, movies: List<MovieHeaderModel>) = previousModel.copy(movieList = movies, isLoading = false)
    fun updateModelWithError(previousModel: MovieFragmentModel)  = previousModel.copy(isError = true, isLoading = false)
}