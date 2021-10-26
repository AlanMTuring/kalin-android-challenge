package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.MovieFragmentViewModel
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentDashboardBinding

    @Inject
    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecycler.layoutManager = LinearLayoutManager(view.context)
        binding.moviesRecycler.adapter = movieListAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMovies()

        viewModel.observableModel.observe(this, { model ->
            binding.model = MovieListFragmentBindingModel(model.isLoading, model.isError)
            movieListAdapter.update(model.movieList)
        })
    }
}

data class MovieListFragmentBindingModel(val isLoading: Boolean,
                                         val isError: Boolean) {
    val showContent = !isLoading && !isError
}

