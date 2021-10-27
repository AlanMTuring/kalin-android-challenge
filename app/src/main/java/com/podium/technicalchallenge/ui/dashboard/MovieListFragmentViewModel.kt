package com.podium.technicalchallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListFragmentViewModel @Inject constructor(private val modelFactory: MovieFragmentModelFactory, private val movieListFragmentEventFactory: MovieListFragmentEventFactory) : ViewModel() {

    private val liveModel: MutableLiveData<MovieFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<MovieFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!

    private val eventPublisher: MutableLiveData<MovieEvent<MovieListFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<MovieListFragment>> = eventPublisher

    fun getMovies() {
        try {
            val headers = Repo.getInstance().getMovieHeaders()
            liveModel.value = modelFactory.updateModelWithMovieHeaders(latestModel, headers)
        } catch (e: Exception) {
            liveModel.value = modelFactory.updateModelWithError(latestModel)
        }
    }

    fun onMovieClicked(movieId: Int) {
        eventPublisher.value = movieListFragmentEventFactory.createOnMovieClickedEvent(movieId)
    }
}
