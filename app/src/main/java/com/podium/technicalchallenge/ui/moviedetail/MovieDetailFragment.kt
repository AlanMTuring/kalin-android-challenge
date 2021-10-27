package com.podium.technicalchallenge.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.podium.technicalchallenge.databinding.FragmentMovieDetailBinding
import com.podium.technicalchallenge.ui.dashboard.MovieHeaderBindingModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    companion object {
        const val MOVIE_ID_KEY = "arg:movieId"
    }

    private val viewModel: MovieDetailFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentMovieDetailBinding

    @Inject
    lateinit var movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val movieId = arguments?.getInt(MOVIE_ID_KEY)
        viewModel.loadMovieDetail(movieId!!)

        viewModel.observableModel.observe(this) { model ->
            binding.model = MovieDetailFragmentBindingModel(model.isLoading, model.isError, model.movieDetail, movieHeaderBindingModelFactory)
        }
    }

}

data class MovieDetailFragmentBindingModel(val isLoading: Boolean,
                                           val isError: Boolean,
                                           private val movieDetail: MovieDetailModel,
                                           private val movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory) {

    val movieHeader = movieHeaderBindingModelFactory.create(movieDetail.header)
    val movieTitle = "${movieHeader.title} (${movieHeader.releaseDate.year})"
    val movieMetadata = "${movieHeader.formattedReleaseDate} • ${movieHeader.formattedRuntime}"
    val formattedRating = "Rated ${movieDetail.voteAverage}/10 (by ${movieDetail.voteCount} users)"
    val formattedDirector = "Directed by: ${movieDetail.director.name}"
    val overview = movieDetail.overview
}