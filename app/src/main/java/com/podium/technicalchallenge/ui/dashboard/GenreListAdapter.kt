package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemGenreBinding
import javax.inject.Inject

class GenreListAdapter @Inject constructor() : RecyclerView.Adapter<GenreListViewHolder>() {

    lateinit var genres: List<String>
    lateinit var genreClickListener: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater, parent, false)
        val viewHolder = GenreListViewHolder(binding)
        binding.seeAllButton.setOnClickListener {
            val adapterPosition = viewHolder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                genreClickListener(genres[adapterPosition])
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    fun update(updatedGenres: List<String>) {
        genres = updatedGenres
    }
}

class GenreListViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: String) {
        binding.genre = genre
    }

}
