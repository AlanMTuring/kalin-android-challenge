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

    private val viewModel: MovieDetailFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.loadMovieDetail()

        viewModel.observableModel.observe(this) { model ->
            binding.model = MovieDetailFragmentBindingModel(model.isLoading, model.isError, model.movieDetailDto?.title ?: "")
        }
    }

}

data class MovieDetailFragmentBindingModel(val isLoading: Boolean,
                                           val isError: Boolean,
                                           val movieTitle: String)