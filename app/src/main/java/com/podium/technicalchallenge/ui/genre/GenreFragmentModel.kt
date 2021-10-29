package com.podium.technicalchallenge.ui.genre

import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import com.podium.technicalchallenge.ui.dashboard.MovieListModel
import javax.inject.Inject

data class GenreFragmentModel(val genreName: String,
                              val movieListModel: MovieListModel)

class GenreFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = GenreFragmentModel(genreName = "", movieListModel = MovieListModel(isLoading = true, isError = false, movieList = listOf()))
    fun updateModelWithGenre(previousModel: GenreFragmentModel, genre: String) = previousModel.copy(genreName = genre)
    fun updateModelWithMovieList(previousModel: GenreFragmentModel, movies: List<MovieHeaderModel>): GenreFragmentModel {
        val previousMovieListModel = previousModel.movieListModel
        return previousModel.copy(movieListModel = previousMovieListModel.copy(movieList = movies, isLoading = false))
    }
    fun updateModelWithError(previousModel: GenreFragmentModel): GenreFragmentModel {
        val previousMovieListModel = previousModel.movieListModel
        return previousModel.copy(movieListModel = previousMovieListModel.copy(isError = true, isLoading = false))
    }
    fun updateModelWithLoading(previousModel: GenreFragmentModel): GenreFragmentModel {
        val previousMovieListModel = previousModel.movieListModel
        return previousModel.copy(movieListModel = previousMovieListModel.copy(isLoading = true, isError = false))
    }
}
