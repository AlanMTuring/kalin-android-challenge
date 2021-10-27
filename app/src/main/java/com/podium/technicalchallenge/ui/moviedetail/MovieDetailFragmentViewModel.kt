package com.podium.technicalchallenge.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.common.MovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(private val modelFactory: MovieDetailFragmentModelFactory, private val eventFactory: MovieDetailFragmentEventFactory): ViewModel() {

    private val liveModel: MutableLiveData<MovieDetailFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<MovieDetailFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!


    private val eventPublisher: MutableLiveData<MovieEvent<MovieDetailFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<MovieDetailFragment>> = eventPublisher

    fun loadMovieDetail(movieId: Int) {
        val detail = Repo.getInstance().getMovieDetail(movieId)
        liveModel.value = modelFactory.updateModelWithDetail(latestModel, detail)
    }

    fun onGenreChipClicked(genre: String) {
        eventPublisher.value = eventFactory.createOnGenreChipClickedEvent(genre)
    }
}