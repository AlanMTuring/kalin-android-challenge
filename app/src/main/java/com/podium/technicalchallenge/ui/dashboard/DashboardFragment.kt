package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardFragmentViewModel by activityViewModels()
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
            binding.model = MovieListFragmentBindingModel(model.isLoading, model.isError)
            viewPagerAdapter.updateBrowseAllList(model.allMovies)
            viewPagerAdapter.updateGenreList(model.genres)
            viewPagerAdapter.updateTopFiveList(model.topFiveList)
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

data class MovieListFragmentBindingModel(val isLoading: Boolean,
                                         val isError: Boolean) {
    val showContent = !isLoading && !isError
}

