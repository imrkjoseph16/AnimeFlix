package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.SearchAnimeItemViewDto
import com.example.animenation.databinding.SharedSearchAnimeItemBinding

fun <T : Any> getSearchAnimeItemBinder(
    dtoRetriever: (T) -> SearchAnimeItemViewDto,
    onItemClick: (SearchAnimeItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedSearchAnimeItemBinding, T> {

    override fun bind(binder: SharedSearchAnimeItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            anime = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedSearchAnimeItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}