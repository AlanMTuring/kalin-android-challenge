package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.databinding.ItemMovieCardBinding
import javax.inject.Inject


class MovieListAdapter @Inject constructor(private val movieHeaderBindingModelFactory: MovieHeaderBindingModelFactory) : RecyclerView.Adapter<MovieViewHolder>() {

    private val asyncDiffer: AsyncListDiffer<MovieHeaderModel> = AsyncListDiffer(this, MovieHeaderModelDiffUtilCallback())
    lateinit var movieClickListener: (Int) -> Unit

    fun update(movies: List<MovieHeaderModel>) {
        asyncDiffer.submitList(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieCardBinding.inflate(inflater, parent, false)

        val viewHolder = MovieViewHolder(binding, movieHeaderBindingModelFactory)
        binding.root.setOnClickListener {
            val adapterPosition = viewHolder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val movieId = asyncDiffer.currentList[adapterPosition].id
                movieClickListener(movieId)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        val payload = payloads[0] as? Map<*,*>
        payload?.let { safePayload ->
            if (safePayload.containsKey(MovieHeaderModelDiffUtilCallback.TITLE_KEY)) {
                val title = safePayload[MovieHeaderModelDiffUtilCallback.TITLE_KEY] as String
                holder.bindTitle(title)
            }
            if (safePayload.containsKey(MovieHeaderModelDiffUtilCallback.IMAGE_URL_KEY)) {
                val imageUrl = safePayload[MovieHeaderModelDiffUtilCallback.IMAGE_URL_KEY] as String?
                holder.bindImageUrl(imageUrl)
            }
            if (safePayload.containsKey(MovieHeaderModelDiffUtilCallback.RELEASE_DATE_KEY)) {
                val releaseDate = safePayload[MovieHeaderModelDiffUtilCallback.RELEASE_DATE_KEY] as String
                holder.bindReleaseDate(releaseDate)
            }
            if (safePayload.containsKey(MovieHeaderModelDiffUtilCallback.RUNTIME_KEY)) {
                val runtime = safePayload[MovieHeaderModelDiffUtilCallback.RUNTIME_KEY] as Int
                holder.bindRuntime(runtime)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        holder.unbind()
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
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

    fun bindTitle(title: String) {
        binding.model = binding.model?.copy(title = title)
    }

    fun bindImageUrl(imageUrl: String?) {
        binding.model = binding.model?.copy(imageUrl = imageUrl)
    }

    fun bindReleaseDate(releaseDate: String) {
        binding.model = binding.model?.copy(formattedReleaseDate = movieHeaderBindingModelFactory.formatDate(releaseDate))
    }

    fun bindRuntime(runtime: Int) {
        binding.model = binding.model?.copy(formattedRuntime = movieHeaderBindingModelFactory.formatDuration(runtime))
    }
}
