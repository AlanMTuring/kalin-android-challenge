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

    lateinit var browseGenreBinding: LayoutGenreListBinding
    lateinit var topFiveBinding: LayoutMovieListBinding
    lateinit var browseAllBinding: LayoutMovieListBinding

    lateinit var movieClickListener: (Int) -> Unit
    lateinit var genreClickListener: (String) -> Unit
    lateinit var sortMoviesBy: (SortOptions) -> Unit

    override fun getCount() = 3

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = when (position) {
            0 -> {
                browseGenreBinding = LayoutGenreListBinding.inflate(inflater, container, false)
                browseGenreBinding.genreRecycler.layoutManager = LinearLayoutManager(container.context)
                genresAdapter.genreClickListener = genreClickListener
                browseGenreBinding.genreRecycler.adapter = genresAdapter
                browseGenreBinding.root
            }
            1 -> {
                topFiveBinding = LayoutMovieListBinding.inflate(inflater, container, false)
                topFiveBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                topFiveMoviesAdapter.movieClickListener = movieClickListener
                topFiveBinding.movieListRecycler.adapter = topFiveMoviesAdapter
                topFiveBinding.root
            }
            2 -> {
                browseAllBinding = LayoutMovieListBinding.inflate(inflater, container, false)
                browseAllBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                allMoviesAdapter.movieClickListener = movieClickListener
                browseAllBinding.movieListRecycler.adapter = allMoviesAdapter
                browseAllBinding.showSortBy = true
                val sortAdapter = ArrayAdapter.createFromResource(container.context, R.array.sort_options, R.layout.item_sort_spinner_option)
                browseAllBinding.sortSpinner.adapter = sortAdapter
                browseAllBinding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                browseAllBinding.root
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

    fun updateGenreList(list: List<String>) {
        genresAdapter.update(list)
    }

    fun updateTopFiveList(list: List<MovieHeaderModel>) {
        topFiveMoviesAdapter.update(list)
    }

    fun updateBrowseAllList(list: List<MovieHeaderModel>){//}, runnable: Runnable) {
        allMoviesAdapter.update(list)//, runnable)
    }
}