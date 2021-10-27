package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import androidx.navigation.NavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.common.MovieEvent
import com.podium.technicalchallenge.ui.moviedetail.MovieDetailFragment
import javax.inject.Inject


class DashboardFragmentEventFactory @Inject constructor() {
    fun createOnMovieClickedEvent(movieId: Int) = OnMovieClickedEvent(movieId)
}

class OnMovieClickedEvent(private val movieId: Int): MovieEvent<DashboardFragment>() {
    override fun doExecute(fragment: DashboardFragment, navController: NavController) {
        val bundle = Bundle()
        bundle.putInt(MovieDetailFragment.MOVIE_ID_KEY, movieId)
        navController.navigate(R.id.action_navigation_dashboard_to_movieDetailFragment, bundle)
    }
}