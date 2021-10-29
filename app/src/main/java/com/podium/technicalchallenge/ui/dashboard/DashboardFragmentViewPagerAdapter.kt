package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.LayoutGenreListBinding
import com.podium.technicalchallenge.databinding.LayoutMovieListBinding
import com.podium.technicalchallenge.ui.dashboard.movielist.MovieListAdapter
import com.podium.technicalchallenge.ui.genre.SortOptions
import javax.inject.Inject

class DashboardFragmentViewPagerAdapter @Inject constructor(private val genresAdapter: GenreListAdapter,
                                                            private val topFiveMoviesAdapter: MovieListAdapter,
                                                            private val allMoviesAdapter: MovieListAdapter) : PagerAdapter() {

    private var browseGenreBinding: LayoutGenreListBinding? = null
    private var topFiveBinding: LayoutMovieListBinding? = null
    var browseAllBinding: LayoutMovieListBinding? = null

    lateinit var movieClickListener: (Int) -> Unit
    lateinit var genreClickListener: (String) -> Unit
    lateinit var imageClickListener: (String?) -> Unit
    lateinit var sortMoviesBy: (SortOptions) -> Unit
    lateinit var topFiveTryAgainClicked: () -> Unit
    lateinit var browseAllTryAgainClicked: () -> Unit
    lateinit var browseGenresTryAgainClicked: () -> Unit

    override fun getCount() = 3

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = when (position) {
            0 -> {
                browseGenreBinding = LayoutGenreListBinding.inflate(inflater, container, false)
                val safeBinding = browseGenreBinding!!
                safeBinding.genreRecycler.layoutManager = LinearLayoutManager(container.context)
                genresAdapter.genreClickListener = genreClickListener
                safeBinding.genreRecycler.adapter = genresAdapter
                safeBinding.tryAgainButton.setOnClickListener { browseGenresTryAgainClicked() }
                safeBinding.root
            }
            1 -> {
                topFiveBinding = LayoutMovieListBinding.inflate(inflater, container, false)
                val safeBinding = topFiveBinding!!
                safeBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                topFiveMoviesAdapter.movieClickListener = movieClickListener
                topFiveMoviesAdapter.imageClickListener = imageClickListener
                safeBinding.movieListRecycler.adapter = topFiveMoviesAdapter
                safeBinding.tryAgainButton.setOnClickListener { topFiveTryAgainClicked() }
                safeBinding.root
            }
            2 -> {
                browseAllBinding = LayoutMovieListBinding.inflate(inflater, container, false)
                val safeBinding = browseAllBinding!!
                safeBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                allMoviesAdapter.movieClickListener = movieClickListener
                allMoviesAdapter.imageClickListener = imageClickListener
                safeBinding.movieListRecycler.adapter = allMoviesAdapter
                safeBinding.tryAgainButton.setOnClickListener { browseAllTryAgainClicked() }
                val sortAdapter = ArrayAdapter.createFromResource(container.context, R.array.sort_options, R.layout.item_sort_spinner_option)
                safeBinding.sortSpinner.adapter = sortAdapter
                safeBinding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        when (position) {
                            0 -> sortMoviesBy(SortOptions.Title)
                            1 -> sortMoviesBy(SortOptions.Popularity)
                            2 -> sortMoviesBy(SortOptions.ReleaseDate)
                            3 -> sortMoviesBy(SortOptions.Rating)
                            4 -> sortMoviesBy(SortOptions.NumberOfRatings)
                            5 -> sortMoviesBy(SortOptions.Duration)
                        }
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) { }
                }
                safeBinding.root
            }
            else -> null
        }

        container.addView(view)
        return view!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Browse Genres"
            1 -> "Top 5"
            2 -> "Browse All"
            else -> null
        }
    }

    fun updateGenreList(genres: List<String>, bindingModel: GenresListBindingModel) {
        genresAdapter.update(genres)
        browseGenreBinding?.model = bindingModel
    }

    fun updateTopFiveList(movieList: List<MovieHeaderModel>, bindingModel: MovieListBindingModel) {
        topFiveMoviesAdapter.update(movieList)
        topFiveBinding?.model = bindingModel
    }

    fun updateBrowseAllList(movieList: List<MovieHeaderModel>, bindingModel: MovieListBindingModel, runnable: Runnable) {
        allMoviesAdapter.update(movieList, runnable)
        browseAllBinding?.model = bindingModel
    }
}