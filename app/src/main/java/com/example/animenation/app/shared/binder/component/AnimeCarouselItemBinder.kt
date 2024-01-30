package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.databinding.SharedAnimeCarouselItemBinding

fun <T : Any> getAnimeCarouselItemBinder(
    dtoRetriever: (T) -> AnimeItemViewDto,
    onItemClick: (AnimeItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedAnimeCarouselItemBinding, T> {

    override fun bind(binder: SharedAnimeCarouselItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            anime = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedAnimeCarouselItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}