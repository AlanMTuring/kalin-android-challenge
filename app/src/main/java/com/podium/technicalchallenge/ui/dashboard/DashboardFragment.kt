package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardFragmentViewModel by viewModels()
    private lateinit var binding: FragmentDashboardBinding

    @Inject
    lateinit var viewPagerAdapter: DashboardFragmentViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter.movieClickListener = viewModel::onMovieClicked
        viewPagerAdapter.genreClickListener = viewModel::onGenreClicked
        viewPagerAdapter.sortMoviesBy = viewModel::sortMoviesBy
        viewPagerAdapter.browseGenresTryAgainClicked = viewModel::browseGenresTryAgainClicked
        viewPagerAdapter.topFiveTryAgainClicked = viewModel::topFiveTryAgainClicked
        viewPagerAdapter.browseAllTryAgainClicked = viewModel::browseAllTryAgainClicked
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = 2
            binding.tabLayout.setupWithViewPager(this)
        }
        binding.viewPager.currentItem = 1 //top 5
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()

        viewModel.observableModel.observe(this) { model ->
            viewPagerAdapter.updateGenreList(model.genresModel.genres, GenresListBindingModel(model.genresModel.isLoading, model.genresModel.isError),)
            viewPagerAdapter.updateTopFiveList(model.topFiveModel.movieList, MovieListBindingModel(false, model.topFiveModel.isLoading, model.topFiveModel.isError))
            viewPagerAdapter.updateBrowseAllList(model.allMoviesModel.movieList, MovieListBindingModel(true, model.allMoviesModel.isLoading, model.allMoviesModel.isError)) {
                viewPagerAdapter.browseAllBinding?.movieListRecycler?.scrollToPosition(0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.observableEvents.observe(this, { event ->
            event.execute(this, findNavController())
        })
    }

    override fun onPause() {
        viewModel.observableEvents.removeObservers(this)
        super.onPause()
    }
}


data class MovieListBindingModel(val isSortable: Boolean, val isLoading: Boolean, val isError: Boolean) {
    val showContent = !isLoading && !isError
    val showSortBy = !isLoading && !isError && isSortable
}

data class GenresListBindingModel(val isLoading: Boolean, val isError: Boolean) {
    val showContent = !isLoading && !isError
}
