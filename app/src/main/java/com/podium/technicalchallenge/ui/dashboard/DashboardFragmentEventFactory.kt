package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import androidx.navigation.NavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.common.MovieEvent
import com.podium.technicalchallenge.ui.ImageDialogFragment
import com.podium.technicalchallenge.ui.genre.GenreFragment
import com.podium.technicalchallenge.ui.moviedetail.MovieDetailFragment
import javax.inject.Inject


class DashboardFragmentEventFactory @Inject constructor() {
    fun createOnMovieClickedEvent(movieId: Int) = OnMovieClickedEvent(movieId)
    fun createOnGenreClickedEvent(genre: String) = OnGenreClickedEvent(genre)
    fun createOnImageClickedEvent(imageUrl: String?) = OnImageClickedEvent(imageUrl)
}

class OnMovieClickedEvent(private val movieId: Int): MovieEvent<DashboardFragment>() {
    override fun doExecute(fragment: DashboardFragment, navController: NavController) {
        val bundle = Bundle()
        bundle.putInt(MovieDetailFragment.MOVIE_ID_KEY, movieId)
        navController.navigate(R.id.action_dashboardFragment_to_movieDetailFragment, bundle)
    }
}

class OnGenreClickedEvent(private val genre: String): MovieEvent<DashboardFragment>() {
    override fun doExecute(fragment: DashboardFragment, navController: NavController) {
        val bundle = Bundle()
        bundle.putString(GenreFragment.GENRE_KEY, genre)
        navController.navigate(R.id.action_dashboardFragment_to_genreFragment, bundle)
    }
}

class OnImageClickedEvent(private val imageUrl: String?) : MovieEvent<DashboardFragment>() {
    override fun doExecute(fragment: DashboardFragment, navController: NavController) {
        val bundle = Bundle()
        bundle.putString(ImageDialogFragment.ARG_IMAGE_URL, imageUrl)
        navController.navigate(R.id.action_dashboardFragment_to_imageDialog, bundle)
    }
}