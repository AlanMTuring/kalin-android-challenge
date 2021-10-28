package com.podium.technicalchallenge.ui.moviedetail

import android.os.Bundle
import androidx.navigation.NavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.common.MovieEvent
import com.podium.technicalchallenge.ui.genre.GenreFragment
import javax.inject.Inject

class MovieDetailFragmentEventFactory @Inject constructor() {
    fun createOnGenreChipClickedEvent(genre: String) = OnGenreChipClickedEvent(genre)

}

class OnGenreChipClickedEvent(private val genre: String) : MovieEvent<MovieDetailFragment>() {
    override fun doExecute(fragment: MovieDetailFragment, navController: NavController) {
        val bundle = Bundle()
        bundle.putString(GenreFragment.GENRE_KEY, genre)
        navController.navigate(R.id.action_movieDetailFragment_to_genreFragment, bundle)
    }
}
