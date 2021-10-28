package com.podium.technicalchallenge.ui.dashboard

import javax.inject.Inject

data class DashboardFragmentModel(val isLoading: Boolean,
                                  val isError: Boolean,
                                  val allMovies: List<MovieHeaderModel>,
                                  val genres: List<String>,
                                  val topFiveList: List<MovieHeaderModel>)

data class MovieHeaderModel(val id: Int = 0,
                            val title: String = "",
                            val runtime: Int = 0,
                            val releaseDate: String = "",
                            val popularity: Double = 0.0,
                            val rating: Double = 0.0,
                            val numberOfRatings: Int = 0,
                            val imageUrl: String? = null)

class DashboardFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = DashboardFragmentModel(isLoading = true, isError = false, allMovies = listOf(), genres = listOf(), topFiveList = listOf())
    fun updateModelWithAllMovies(previousModel: DashboardFragmentModel, movies: List<MovieHeaderModel>) = previousModel.copy(allMovies = movies, isLoading = false)
    fun updateModelWithTopFiveMovies(previousModel: DashboardFragmentModel, movies: List<MovieHeaderModel>) = previousModel.copy(topFiveList = movies, isLoading = false)
    fun updateModelWithError(previousModel: DashboardFragmentModel)  = previousModel.copy(isError = true, isLoading = false)
    fun updateModelWithGenres(previousModel: DashboardFragmentModel, genres: List<String>) = previousModel.copy(genres = genres)
}