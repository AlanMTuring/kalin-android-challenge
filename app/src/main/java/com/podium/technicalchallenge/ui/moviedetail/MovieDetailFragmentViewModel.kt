package com.podium.technicalchallenge.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.podium.technicalchallenge.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(private val modelFactory: MovieDetailFragmentModelFactory): ViewModel() {

    private val liveModel: MutableLiveData<MovieDetailFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<MovieDetailFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!

    fun loadMovieDetail(movieId: Int) {
        val detail = Repo.getInstance().getMovieDetail(movieId)
        liveModel.value = modelFactory.updateModelWithDetail(latestModel, detail)
    }
}