package com.podium.technicalchallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(private val modelFactory: DashboardFragmentModelFactory, private val eventFactory: DashboardFragmentEventFactory) : ViewModel() {

    private val liveModel: MutableLiveData<DashboardFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<DashboardFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!

    private val eventPublisher: MutableLiveData<MovieEvent<DashboardFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<DashboardFragment>> = eventPublisher

    fun getMovies() {
        try {
            val headers = Repo.getInstance().getMovieHeaders()
            liveModel.value = modelFactory.updateModelWithMovieHeaders(latestModel, headers)
        } catch (e: Exception) {
            liveModel.value = modelFactory.updateModelWithError(latestModel)
        }
    }

    fun onMovieClicked(movieId: Int) {
        eventPublisher.value = eventFactory.createOnMovieClickedEvent(movieId)
    }
}
