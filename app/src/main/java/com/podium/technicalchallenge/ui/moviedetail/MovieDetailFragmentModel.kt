package com.podium.technicalchallenge.ui.moviedetail

import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import javax.inject.Inject

data class MovieDetailFragmentModel(val isLoading: Boolean,
                                    val isError: Boolean,
                                    val movieDetail: MovieDetailModel)

data class MovieDetailModel(val header: MovieHeaderModel = MovieHeaderModel(),
                            val originalLanguage: String = "",
                            val originalTitle: String = "",
                            val overview: String = "",
                            val voteAverage: Double = 0.0,
                            val voteCount: Int = 0,
                            val genres: List<String> = listOf(),
                            val cast: List<CastMemberModel> = listOf(),
                            val director: DirectorModel = DirectorModel()
)

data class CastMemberModel(val imageUrl: String?,
                      val name: String,
                      val character: String,
                      val order: Int)

data class DirectorModel(val id: Int = 0, val name: String = "")

class MovieDetailFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieDetailFragmentModel(isLoading = true, isError = false, movieDetail = MovieDetailModel())
    fun updateModelWithDetail(previousModel: MovieDetailFragmentModel, detail: MovieDetailModel) = previousModel.copy(movieDetail = detail)
}