package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.ui.dashboard.MovieHeader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(private val modelFactory: MovieFragmentModelFactory) : ViewModel() {
    val TAG = "DemoViewModel"

    private val liveModel: MutableLiveData<MovieFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<MovieFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!


    fun getMovies() {
        viewModelScope.launch() {
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
}

data class MovieFragmentModel(val isLoading: Boolean,
                              val isError: Boolean,
                              val movieList: List<MovieHeader>) {

}

class MovieFragmentModelFactory @Inject constructor() {
    fun createLoadingModel() = MovieFragmentModel(isLoading = true, isError = false, movieList = listOf())
    fun updateModelWithMovieHeaders(latestModel: MovieFragmentModel, movies: List<MovieHeader>) = latestModel.copy(movieList = movies, isLoading = false)
}