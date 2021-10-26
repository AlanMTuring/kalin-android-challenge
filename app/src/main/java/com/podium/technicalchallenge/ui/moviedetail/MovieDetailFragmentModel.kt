package com.podium.technicalchallenge.ui.moviedetail

import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import javax.inject.Inject

data class MovieDetailFragmentModel(val isLoading: Boolean,
                                    val isError: Boolean,
                                    val movieDetail: MovieDetailModel?)

data class MovieDetailModel(val header: MovieHeaderModel,
                            val title: String)

class MovieDetailFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieDetailFragmentModel(isLoading = true, isError = false, movieDetail = null)
    fun updateModelWithDetail(previousModel: MovieDetailFragmentModel, detail: MovieDetailModel) = previousModel.copy(movieDetail = detail)
}