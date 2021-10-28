package com.podium.technicalchallenge.ui.genre

import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import javax.inject.Inject

data class GenreFragmentModel(val isLoading: Boolean,
                              val isError: Boolean,
                              val genreName: String,
                              val showSortBy: Boolean,
                              val movies: List<MovieHeaderModel>)

class GenreFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = GenreFragmentModel(isLoading = true, isError = false, genreName = "", movies = listOf(), showSortBy = true)
    fun updateModelWithMovieList(previousModel: GenreFragmentModel, movies: List<MovieHeaderModel>) = previousModel.copy(movies = movies)
    fun updateModelWithGenre(previousModel: GenreFragmentModel, genre: String) = previousModel.copy(genreName = genre)
}
