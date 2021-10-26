package com.podium.technicalchallenge.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.podium.technicalchallenge.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    companion object {
        const val MOVIE_ID_KEY = "arg:movieId"
    }

    private val viewModel: MovieDetailFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val movieId = arguments?.getInt(MOVIE_ID_KEY)
        viewModel.loadMovieDetail(movieId!!)

        viewModel.observableModel.observe(this) { model ->
            binding.model = MovieDetailFragmentBindingModel(model.isLoading, model.isError, model.movieDetail)
        }
    }

}

data class MovieDetailFragmentBindingModel(val isLoading: Boolean,
                                           val isError: Boolean,
                                           private val movieDetail: MovieDetailModel?) {
    val title: String = movieDetail?.header?.id.toString()

}