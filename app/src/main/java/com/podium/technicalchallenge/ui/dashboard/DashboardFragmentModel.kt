package com.podium.technicalchallenge.ui.dashboard

import javax.inject.Inject

data class DashboardFragmentModel(val genresModel: GenreListModel,
                    val topFiveModel: MovieListModel,
                    val allMoviesModel: MovieListModel)

data class GenreListModel(val isLoading: Boolean,
                          val isError: Boolean,
                          val genres: List<String>)

data class MovieListModel(val isLoading: Boolean,
                          val isError: Boolean,
                          val movieList: List<MovieHeaderModel>)

data class MovieHeaderModel(val id: Int = 0,
                            val title: String = "",
                            val runtime: Int = 0,
                            val releaseDate: String = "",
                            val popularity: Double = 0.0,
                            val rating: Double = 0.0,
                            val numberOfRatings: Int = 0,
                            val imageUrl: String? = null)

class DashboardFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = DashboardFragmentModel(
        GenreListModel(isLoading = true, isError = false, genres = listOf()),
        MovieListModel(isLoading = true, isError = false, movieList = listOf()),
        MovieListModel(isLoading = true, isError = false, movieList= listOf())
    )

    fun updateModelWithAllMovies(previousModel: DashboardFragmentModel, movies: List<MovieHeaderModel>): DashboardFragmentModel {
        val previousAllMoviesModel = previousModel.allMoviesModel
        return previousModel.copy(allMoviesModel = previousAllMoviesModel.copy(movieList = movies, isLoading = false))
    }
    fun updateModelWithAllMoviesError(previousModel: DashboardFragmentModel): DashboardFragmentModel {
        val previousAllMoviesModel = previousModel.allMoviesModel
        return previousModel.copy(allMoviesModel = previousAllMoviesModel.copy(isError = true, isLoading = false))
    }
    fun updateModelWithAllMoviesLoading(previousModel: DashboardFragmentModel) = previousModel.copy(allMoviesModel = MovieListModel(isLoading = true, isError = false, movieList = listOf()))

    fun updateModelWithTopFiveMovies(previousModel: DashboardFragmentModel, movies: List<MovieHeaderModel>): DashboardFragmentModel {
        val previousTopFiveModel = previousModel.topFiveModel
        return previousModel.copy(topFiveModel = previousTopFiveModel.copy(movieList = movies, isLoading = false))
    }
    fun updateModelWithTopFiveMoviesError(previousModel: DashboardFragmentModel): DashboardFragmentModel {
        val previousTopFiveModel = previousModel.topFiveModel
        return previousModel.copy(topFiveModel = previousTopFiveModel.copy(isError = true, isLoading = false))
    }
    fun updateModelWithTopFiveLoading(previousModel: DashboardFragmentModel) = previousModel.copy(topFiveModel = MovieListModel(isLoading = true, isError = false, movieList = listOf()))

    fun updateModelWithGenres(previousModel: DashboardFragmentModel, genres: List<String>): DashboardFragmentModel {
        val previousGenresModel = previousModel.genresModel
        return previousModel.copy(genresModel = previousGenresModel.copy(genres = genres, isLoading = false))
    }
    fun updateModelWithGenresError(previousModel: DashboardFragmentModel): DashboardFragmentModel {
        val previousGenresModel = previousModel.genresModel
        return previousModel.copy(genresModel = previousGenresModel.copy(isError = true, isLoading = false))
    }
    fun updateModelWithGenresLoading(previousModel: DashboardFragmentModel) = previousModel.copy(genresModel = GenreListModel(isLoading = true, isError = false, genres = listOf()))

}