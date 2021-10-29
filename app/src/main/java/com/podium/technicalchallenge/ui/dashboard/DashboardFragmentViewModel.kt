package com.podium.technicalchallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import com.podium.technicalchallenge.ui.genre.SortOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(private val modelFactory: DashboardFragmentModelFactory, private val eventFactory: DashboardFragmentEventFactory) : ViewModel() {

    private val liveModel: MutableLiveData<DashboardFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<DashboardFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!

    private val eventPublisher: MutableLiveData<MovieEvent<DashboardFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<DashboardFragment>> = eventPublisher

    fun fetchData() {
        viewModelScope.launch {
            getGenres()
            getTopFiveMovies()
            getAllMovies()
        }
    }

    private fun getGenres() {
        viewModelScope.launch {
            try {
                val genres = withContext(Dispatchers.IO) {
                    Repo.getInstance().getGenres().sorted()
                }
                liveModel.value = modelFactory.updateModelWithGenres(latestModel, genres)
            } catch (ex: Exception) {
                liveModel.value = modelFactory.updateModelWithGenresError(latestModel)
            }
        }
    }

    private fun getTopFiveMovies() {
        viewModelScope.launch {
            try {
                val top5Headers = withContext(Dispatchers.IO) {
                    Repo.getInstance().getTopFiveHeaders()
                }
                liveModel.value = modelFactory.updateModelWithTopFiveMovies(latestModel, top5Headers)
            } catch (ex: Exception) {
                liveModel.value = modelFactory.updateModelWithTopFiveMoviesError(latestModel)
            }
        }
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            try {
                val headers = withContext(Dispatchers.IO) {
                    Repo.getInstance().getMovieHeaders().sortedBy { it.title }
                }
                liveModel.value = modelFactory.updateModelWithAllMovies(latestModel, headers)
            } catch (ex: Exception) {
                liveModel.value = modelFactory.updateModelWithAllMoviesError(latestModel)
            }
        }
    }

    fun onMovieClicked(movieId: Int) {
        eventPublisher.value = eventFactory.createOnMovieClickedEvent(movieId)
    }

    fun onGenreClicked(genre: String) {
        eventPublisher.value = eventFactory.createOnGenreClickedEvent(genre)
    }

    fun sortMoviesBy(options: SortOptions) {
        if (latestModel.allMoviesModel.isLoading) return
        val safeModel = latestModel
        val allMoviesModel = latestModel.allMoviesModel
        val sortedList = when (options) {
            SortOptions.Title -> allMoviesModel.movieList.sortedBy { it.title }
            SortOptions.Popularity -> allMoviesModel.movieList.sortedByDescending { it.popularity }
            SortOptions.ReleaseDate -> allMoviesModel.movieList.sortedByDescending { it.releaseDate }
            SortOptions.Rating -> allMoviesModel.movieList.sortedByDescending { it.rating }
            SortOptions.NumberOfRatings -> allMoviesModel.movieList.sortedByDescending { it.numberOfRatings }
            SortOptions.Duration -> allMoviesModel.movieList.sortedByDescending { it.runtime }
        }
        liveModel.value = modelFactory.updateModelWithAllMovies(safeModel, sortedList)
    }
}
