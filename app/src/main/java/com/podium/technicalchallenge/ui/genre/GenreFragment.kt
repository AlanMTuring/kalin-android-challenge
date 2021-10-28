package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.databinding.FragmentGenreBinding
import com.podium.technicalchallenge.ui.dashboard.movielist.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


//TODO fix last element getting cut off
//TODO clicks for movies
@AndroidEntryPoint
class GenreFragment : Fragment() {

    companion object {
        const val GENRE_KEY = "arg:genre"
    }

    private val viewModel: GenreFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentGenreBinding

    @Inject
    lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGenreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieAdapter.movieClickListener = viewModel::onMovieClicked
        binding.movieList.movieListRecycler.adapter = movieAdapter
        binding.movieList.movieListRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()

        val genre = arguments?.getString(GENRE_KEY)!!
        viewModel.loadGenreMovies(genre)

        viewModel.observableModel.observe(this) { model ->
            binding.model = GenreFragmentBindingModel(model.isLoading, model.isError, model.genreName)
            movieAdapter.update(model.movies)
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

data class GenreFragmentBindingModel(val isLoading: Boolean, val isError: Boolean, val name: String)