package com.podium.technicalchallenge.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class GenreFragmentViewModel @Inject constructor(private val modelFactory: GenreFragmentModelFactory, private val eventFactory: GenreFragmentEventFactory) : ViewModel() {

    private val liveModel: MutableLiveData<GenreFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<GenreFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!


    private val eventPublisher: MutableLiveData<MovieEvent<GenreFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<GenreFragment>> = eventPublisher

    fun loadGenreMovies(genre: String) {
        viewModelScope.launch {
            try {
                liveModel.value = modelFactory.updateModelWithGenre(latestModel, genre)
                val movies = withContext(Dispatchers.IO) {
                    Repo.getInstance().getMoviesByGenre(genre).sortedBy { it.title }
                }
                liveModel.value = modelFactory.updateModelWithMovieList(latestModel, movies)
            } catch (ex: Exception) {
                //todo handle exception
            }
        }
    }

    fun onMovieClicked(movieId: Int) {
        eventPublisher.value = eventFactory.createOnMovieClickedEvent(movieId)
    }

    fun sortMoviesBy(options: SortOptions) {
        if (latestModel.movieListModel.isLoading) return
        val safeModel = latestModel
        val sortedList = when (options) {
            SortOptions.Title -> safeModel.movieListModel.movieList.sortedBy { it.title }
            SortOptions.Popularity -> safeModel.movieListModel.movieList.sortedByDescending { it.popularity }
            SortOptions.ReleaseDate -> safeModel.movieListModel.movieList.sortedByDescending { it.releaseDate }
            SortOptions.Rating -> safeModel.movieListModel.movieList.sortedByDescending { it.rating }
            SortOptions.NumberOfRatings -> safeModel.movieListModel.movieList.sortedByDescending { it.numberOfRatings }
            SortOptions.Duration -> safeModel.movieListModel.movieList.sortedByDescending { it.runtime }
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