package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.podium.technicalchallenge.databinding.ItemMovieListBinding
import javax.inject.Inject

class DashboardFragmentViewPagerAdapter @Inject constructor(private val genreMoviesAdapter: MovieListAdapter,
                                                            private val topFiveMoviesAdapter: MovieListAdapter,
                                                            private val allMoviesAdapter: MovieListAdapter) : PagerAdapter() {

    lateinit var browseGenreBinding: ItemMovieListBinding
    lateinit var topFiveBinding: ItemMovieListBinding
    lateinit var browseAllBinding: ItemMovieListBinding

    lateinit var movieClickListener: (Int) -> Unit

    override fun getCount() = 3

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = when (position) {
            0 -> {
                browseGenreBinding = ItemMovieListBinding.inflate(inflater, container, false)
                browseGenreBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                genreMoviesAdapter.movieClickListener = movieClickListener
                browseGenreBinding.movieListRecycler.adapter = genreMoviesAdapter
                browseGenreBinding.root
            }
            1 -> {
                topFiveBinding = ItemMovieListBinding.inflate(inflater, container, false)
                topFiveBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                topFiveMoviesAdapter.movieClickListener = movieClickListener
                topFiveBinding.movieListRecycler.adapter = genreMoviesAdapter
                topFiveBinding.root
            }
            2 -> {
                browseAllBinding = ItemMovieListBinding.inflate(inflater, container, false)
                browseAllBinding.movieListRecycler.layoutManager = LinearLayoutManager(container.context)
                allMoviesAdapter.movieClickListener = movieClickListener
                browseAllBinding.movieListRecycler.adapter = genreMoviesAdapter
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
            else -> "Error"
        }
    }

    fun updateGenreList(list: List<MovieHeaderModel>) {
        genreMoviesAdapter.update(list)
    }

    fun updateTopFiveList(list: List<MovieHeaderModel>) {
        topFiveMoviesAdapter.update(list)
    }

    fun updateBrowseAllList(list: List<MovieHeaderModel>) {
        allMoviesAdapter.update(list)
    }
}