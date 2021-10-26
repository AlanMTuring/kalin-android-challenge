package com.podium.technicalchallenge.ui.dashboard

import javax.inject.Inject


data class MovieFragmentModel(val isLoading: Boolean,
                              val isError: Boolean,
                              val movieList: List<MovieHeaderModel>)

data class MovieHeaderModel(val id: Int,
                             val title: String,
                             val runtime: Int,
                             val imageUrl: String?)

class MovieFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieFragmentModel(isLoading = true, isError = false, movieList = listOf())
    fun updateModelWithMovieHeaders(latestModel: MovieFragmentModel, movies: List<MovieHeaderModel>) = latestModel.copy(movieList = movies, isLoading = false)
}