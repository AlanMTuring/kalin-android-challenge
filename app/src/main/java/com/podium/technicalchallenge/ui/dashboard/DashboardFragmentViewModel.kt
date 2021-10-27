package com.podium.technicalchallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

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
        try {
            val top5Headers = Repo.getInstance().getTopFiveHeaders()
            liveModel.value = modelFactory.updateModelWithTopFiveMovies(latestModel, top5Headers)
        } catch (ex: Exception) {

        }
    }

    private fun getAllMovies() {
        try {
            val headers = Repo.getInstance().getMovieHeaders()
            liveModel.value = modelFactory.updateModelWithMovieHeaders(latestModel, headers)
        } catch (ex: Exception) {
            liveModel.value = modelFactory.updateModelWithError(latestModel)
        }
    }

    private fun getGenres() {

    }

    fun onMovieClicked(movieId: Int) {
        eventPublisher.value = eventFactory.createOnMovieClickedEvent(movieId)
    }

    fun onGenreClicked(genre: String) {

    }
}
