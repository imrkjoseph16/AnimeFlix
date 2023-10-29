package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.databinding.SharedAnimeCarouselItemBinding

fun <T : Any> getMovieCarouselItemBinder(
    dtoRetriever: (T) -> AnimeItemViewDto
) = object : RecyclerViewHolder<SharedAnimeCarouselItemBinding, T> {

    override fun bind(binder: SharedAnimeCarouselItemBinding, item: T) {
        with(binder) {
            movie = dtoRetriever(item)
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedAnimeCarouselItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}