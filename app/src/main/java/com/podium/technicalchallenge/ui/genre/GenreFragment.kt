package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentGenreBinding
import com.podium.technicalchallenge.ui.dashboard.MovieListBindingModel
import com.podium.technicalchallenge.ui.dashboard.movielist.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GenreFragment : Fragment() {

    companion object {
        const val GENRE_KEY = "arg:genre"
    }

    private val viewModel: GenreFragmentViewModel by viewModels()
    private lateinit var binding: FragmentGenreBinding

    @Inject
    lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGenreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieAdapter.movieClickListener = viewModel::onMovieClicked
        movieAdapter.imageClickListener = viewModel::onImageClicked
        binding.movieList.movieListRecycler.adapter = movieAdapter
        binding.movieList.movieListRecycler.layoutManager = LinearLayoutManager(context)
        binding.movieList.tryAgainButton.setOnClickListener { viewModel.onTryAgainClicked() }
        val sortAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.sort_options, R.layout.item_sort_spinner_option)
        binding.movieList.sortSpinner.adapter = sortAdapter
        binding.movieList.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> viewModel.sortMoviesBy(SortOptions.Title)
                    1 -> viewModel.sortMoviesBy(SortOptions.Popularity)
                    2 -> viewModel.sortMoviesBy(SortOptions.ReleaseDate)
                    3 -> viewModel.sortMoviesBy(SortOptions.Rating)
                    4 -> viewModel.sortMoviesBy(SortOptions.NumberOfRatings)
                    5 -> viewModel.sortMoviesBy(SortOptions.Duration)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    override fun onStart() {
        super.onStart()

        val genre = arguments?.getString(GENRE_KEY)!!
        viewModel.fetchData(genre)

        viewModel.observableModel.observe(this) { model ->
            binding.model = GenreFragmentBindingModel(
                model.genreName,
                MovieListBindingModel(true, model.movieListModel.isLoading, model.movieListModel.isError)
            )
            movieAdapter.update(model.movieListModel.movieList) {
                binding.movieList.movieListRecycler.scrollToPosition(0)
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

data class GenreFragmentBindingModel(val name: String, val movieListBindingModel: MovieListBindingModel)
