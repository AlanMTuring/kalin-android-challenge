package com.podium.technicalchallenge.ui.moviedetail

import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import javax.inject.Inject

data class MovieDetailFragmentModel(val isLoading: Boolean,
                                    val isError: Boolean,
                                    val movieDetail: MovieDetailModel?)

data class MovieDetailModel(val header: MovieHeaderModel,
                            val overview: String,
                            val genres: List<String>,
                            val cast: List<CastMemberModel>,
                            val director: DirectorModel)

data class CastMemberModel(val imageUrl: String?,
                      val name: String,
                      val character: String,
                      val order: Int)

data class DirectorModel(val id: Int = 0, val name: String = "")

class MovieDetailFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieDetailFragmentModel(isLoading = true, isError = false, movieDetail = null)
    fun updateModelWithDetail(previousModel: MovieDetailFragmentModel, detail: MovieDetailModel) = previousModel.copy(movieDetail = detail, isLoading = false)
    fun updateModelWithError(previousModel: MovieDetailFragmentModel) = previousModel.copy(isError = true, isLoading = false)
}