package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.app.shared.dto.OtherEpisodesItemViewDto
import com.example.animenation.databinding.SharedAnimeCarouselItemBinding
import com.example.animenation.databinding.SharedOtherEpisodesItemBinding

fun <T : Any> getOtherEpisodeItemBinder(
    dtoRetriever: (T) -> OtherEpisodesItemViewDto,
    onItemClick: (OtherEpisodesItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedOtherEpisodesItemBinding, T> {

    override fun bind(binder: SharedOtherEpisodesItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            episodes = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedOtherEpisodesItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}