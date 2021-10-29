package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemGenreBinding
import javax.inject.Inject

class GenreListAdapter @Inject constructor() : RecyclerView.Adapter<GenreListViewHolder>() {

    private val asyncDiffer: AsyncListDiffer<String> = AsyncListDiffer(this, MovieHeaderModelDiffUtilCallback())
    lateinit var genreClickListener: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater, parent, false)
        val viewHolder = GenreListViewHolder(binding)
        binding.seeAllButton.setOnClickListener {
            val adapterPosition = viewHolder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                genreClickListener(asyncDiffer.currentList[adapterPosition])
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    fun update(genres: List<String>) {
        asyncDiffer.submitList(genres)
    }
}

class GenreListViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: String) {
        binding.genre = genre
    }

}

class MovieHeaderModelDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}