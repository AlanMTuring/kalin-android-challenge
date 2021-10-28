package com.podium.technicalchallenge.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreFragmentViewModel @Inject constructor(private val modelFactory: GenreFragmentModelFactory, private val eventFactory: GenreFragmentEventFactory) : ViewModel() {

    private val liveModel: MutableLiveData<GenreFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<GenreFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!


    private val eventPublisher: MutableLiveData<MovieEvent<GenreFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<GenreFragment>> = eventPublisher

    fun loadGenreMovies(genre: String) {
        liveModel.value = modelFactory.updateModelWithGenre(latestModel, genre)
        val movies = Repo.getInstance().getMoviesByGenre(genre).sortedBy { it.title }
        liveModel.value = modelFactory.updateModelWithMovieList(latestModel, movies)
    }

    fun onMovieClicked(movieId: Int) {
        eventPublisher.value = eventFactory.createOnMovieClickedEvent(movieId)
    }

    fun sortMoviesBy(options: SortOptions) {
        val safeModel = latestModel
        val sortedList = when (options) {
            SortOptions.Title -> safeModel.movies.sortedBy { it.title }
            SortOptions.Popularity -> safeModel.movies.sortedByDescending { it.popularity }
            SortOptions.ReleaseDate -> safeModel.movies.sortedByDescending { it.releaseDate }
            SortOptions.Rating -> safeModel.movies.sortedByDescending { it.rating }
            SortOptions.NumberOfRatings -> safeModel.movies.sortedByDescending { it.numberOfRatings }
            SortOptions.Duration -> safeModel.movies.sortedByDescending { it.runtime }
        }
        liveModel.value = modelFactory.updateModelWithMovieList(safeModel, sortedList)
    }
}


enum class SortOptions {
    Title,
    Popularity,
    ReleaseDate,
    Rating,
    NumberOfRatings,
    Duration
}