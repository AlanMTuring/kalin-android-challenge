package com.podium.technicalchallenge.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemCastMemberCardBinding
import javax.inject.Inject

class CastAdapter @Inject constructor() : RecyclerView.Adapter<CastMemberViewHolder>() {

    lateinit var castList: List<CastMemberModel>
    lateinit var imageClickListener: (String?) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMemberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastMemberCardBinding.inflate(inflater, parent, false)
        val viewHolder = CastMemberViewHolder(binding)

        binding.profileImage.setOnClickListener {
            val adapterPosition = viewHolder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val imageUrl = castList[adapterPosition].imageUrl
                imageClickListener(imageUrl)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CastMemberViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun update(cast: List<CastMemberModel>) {
        castList = cast
    }

}

class CastMemberViewHolder(private val binding: ItemCastMemberCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(castMember: CastMemberModel) {
        binding.model = castMember
    }

}
