package com.podium.technicalchallenge.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.FragmentMovieDetailBinding
import com.podium.technicalchallenge.ui.dashboard.movielist.MovieHeaderBindingModelFactory
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
    lateinit var genreChipAdapter: GenreChipAdapter

    @Inject
    lateinit var castAdapter: CastAdapter

    @Inject
    lateinit var movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        genreChipAdapter.genreClickListener = viewModel::onGenreChipClicked
        binding.genreRecycler.adapter = genreChipAdapter
        binding.genreRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.castRecycler.adapter = castAdapter
        binding.castRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()

        val movieId = arguments?.getInt(MOVIE_ID_KEY)
        viewModel.loadMovieDetail(movieId!!)

        viewModel.observableModel.observe(this) { model ->
            binding.model = MovieDetailFragmentBindingModel(model.isLoading, model.isError, model.movieDetail, movieHeaderBindingModelFactory)
            genreChipAdapter.update(model.movieDetail.genres)
            castAdapter.update(model.movieDetail.cast)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.observableEvents.observe(this) { event ->
            event.execute(this, findNavController())
        }
    }

    override fun onPause() {
        viewModel.observableEvents.removeObservers(this)
        super.onPause()
    }
}

data class MovieDetailFragmentBindingModel(val isLoading: Boolean,
                                           val isError: Boolean,
                                           private val movieDetail: MovieDetailModel,
                                           private val movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory) {
    val showContent = !isLoading && !isError
    val movieHeader = movieHeaderBindingModelFactory.create(movieDetail.header)
    val movieTitle = "${movieHeader.title} (${movieHeader.releaseDate.year})"
    val movieMetadata = "${movieHeader.formattedReleaseDate} • ${movieHeader.formattedRuntime}"
    val formattedRating = "Rated ${movieDetail.header.rating}/10 (by ${movieDetail.header.numberOfRatings} users)"
    val formattedDirector = "Directed by: ${movieDetail.director.name}"
    val overview = movieDetail.overview
}