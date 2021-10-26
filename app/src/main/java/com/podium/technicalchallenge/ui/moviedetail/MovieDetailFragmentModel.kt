package com.podium.technicalchallenge.ui.moviedetail

import javax.inject.Inject

data class MovieDetailFragmentModel(val isLoading: Boolean,
                                    val isError: Boolean,
                                    val movieDetailDto: MovieDetailModel?)

data class MovieDetailModel(val title: String)

class MovieDetailFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieDetailFragmentModel(isLoading = true, isError = false, movieDetailDto = null)

}