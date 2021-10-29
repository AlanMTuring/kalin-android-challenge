package com.podium.technicalchallenge.ui.moviedetail

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
class MovieDetailFragmentViewModel @Inject constructor(private val modelFactory: MovieDetailFragmentModelFactory, private val eventFactory: MovieDetailFragmentEventFactory): ViewModel() {

    private val liveModel: MutableLiveData<MovieDetailFragmentModel> = MutableLiveData(modelFactory.createLoadingModel())
    val observableModel: LiveData<MovieDetailFragmentModel> = liveModel
    private val latestModel
        get() = liveModel.value!!


    private val eventPublisher: MutableLiveData<MovieEvent<MovieDetailFragment>> = MutableLiveData()
    val observableEvents: LiveData<MovieEvent<MovieDetailFragment>> = eventPublisher

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                val detail = withContext(Dispatchers.IO) {
                    Repo.getInstance().getMovieDetail(movieId)
                }
                liveModel.value = modelFactory.updateModelWithDetail(latestModel, detail)
            } catch (ex: Exception) {
                liveModel.value = modelFactory.updateModelWithError(latestModel)
            }
        }
    }

    fun onGenreChipClicked(genre: String) {
        eventPublisher.value = eventFactory.createOnGenreChipClickedEvent(genre)
    }

    fun onImageClicked(imageUrl: String?) {
        eventPublisher.value = eventFactory.createOnImageClickedEvent(imageUrl)
    }
}