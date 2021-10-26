package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.podium.technicalchallenge.ui.dashboard.MovieFragmentModel
import com.podium.technicalchallenge.ui.dashboard.MovieFragmentModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFragmentViewModel @Inject constructor(private val modelFactory: MovieFragmentModelFactory) : ViewModel() {
    val TAG = "DemoViewModel"

    private val liveModel: MutableLiveData<MovieFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<MovieFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!


    fun getMovies() {
        try {
            val headers = Repo.getInstance().getMovieHeaders()
            liveModel.value = modelFactory.updateModelWithMovieHeaders(latestModel, headers)
            Log.d(TAG, "movies= $headers")
        } catch (e: Exception) {
            //update UI
            Log.d(TAG, "error getting movie headers")
        }
    }
}