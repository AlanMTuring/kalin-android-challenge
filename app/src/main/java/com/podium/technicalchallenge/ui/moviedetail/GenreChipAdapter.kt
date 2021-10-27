package com.podium.technicalchallenge.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemGenreChipBinding
import javax.inject.Inject

class GenreChipAdapter @Inject constructor() : RecyclerView.Adapter<GenreChipViewHolder>() {

    lateinit var genres: MutableList<String>
    lateinit var genreClickListener: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreChipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreChipBinding.inflate(inflater, parent, false)

        val viewHolder = GenreChipViewHolder(binding)
        binding.root.setOnClickListener {
            val adapterPosition = viewHolder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                genreClickListener(genres[adapterPosition])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: GenreChipViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    fun update(updatedList: List<String>) {
        genres = updatedList.toMutableList()
    }
}

class GenreChipViewHolder(private val binding: ItemGenreChipBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: String) {
        binding.genre = genre
    }

}
