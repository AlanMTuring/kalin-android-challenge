package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.databinding.ItemMovieCardBinding
import javax.inject.Inject


class MovieListAdapter @Inject constructor(private val movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory) : RecyclerView.Adapter<MovieViewHolder>() {

    private val movieList: MutableList<MovieHeaderModel> = mutableListOf()

    fun update(movies: List<MovieHeaderModel>) {
        movieList.clear()
        movieList.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieCardBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding, movieHeaderBindingModelFactory)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        holder.unbind()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}

class MovieViewHolder(private val binding: ItemMovieCardBinding, private val movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieHeaderModel) {
        binding.model = movieHeaderBindingModelFactory.create(movie)
    }

    fun unbind() {
        Glide.with(binding.root).clear(binding.moviePoster)
        binding.moviePoster.setImageDrawable(null)
    }
}
