package com.podium.technicalchallenge.ui.dashboard

import androidx.navigation.NavController
import com.podium.technicalchallenge.common.MovieEvent
import javax.inject.Inject


class MovieListFragmentEventFactory @Inject constructor() {
    fun createOnMovieClickedEvent(movieId: Int) = OnMovieClickedEvent(movieId)
}

class OnMovieClickedEvent(private val movieId: Int): MovieEvent<MovieListFragment>() {
    override fun doExecute(fragment: MovieListFragment, navController: NavController) {
        //todo create the bundle
        //todo navigate to the fragment
    }

}