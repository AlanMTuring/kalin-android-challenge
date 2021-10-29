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
            getTopFiveMovies()
            getAllMovies()
            getGenres()
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
                //todo handle errors
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
                liveModel.value = modelFactory.updateModelWithError(latestModel)
            }
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
                //todo handle errors
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
        val safeModel = latestModel
        val sortedList = when (options) {
            SortOptions.Title -> safeModel.allMovies.sortedBy { it.title }
            SortOptions.Popularity -> safeModel.allMovies.sortedByDescending { it.popularity }
            SortOptions.ReleaseDate -> safeModel.allMovies.sortedByDescending { it.releaseDate }
            SortOptions.Rating -> safeModel.allMovies.sortedByDescending { it.rating }
            SortOptions.NumberOfRatings -> safeModel.allMovies.sortedByDescending { it.numberOfRatings }
            SortOptions.Duration -> safeModel.allMovies.sortedByDescending { it.runtime }
        }
        liveModel.value = modelFactory.updateModelWithAllMovies(safeModel, sortedList)
    }
}
