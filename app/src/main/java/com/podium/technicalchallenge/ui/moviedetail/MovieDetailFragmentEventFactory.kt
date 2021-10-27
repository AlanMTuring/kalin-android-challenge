package com.podium.technicalchallenge.ui.moviedetail

import androidx.navigation.NavController
import com.podium.technicalchallenge.common.MovieEvent
import javax.inject.Inject

class MovieDetailFragmentEventFactory @Inject constructor() {
    fun createOnGenreChipClickedEvent(genre: String) = OnGenreChipClickedEvent(genre)

}

class OnGenreChipClickedEvent(private val genre: String) : MovieEvent<MovieDetailFragment>() {
    override fun doExecute(fragment: MovieDetailFragment, navController: NavController) {
        //todo create bundle
        //todo navigate to genre
    }

}
