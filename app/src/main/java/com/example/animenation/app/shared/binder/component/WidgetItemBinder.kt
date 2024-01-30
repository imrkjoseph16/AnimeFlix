package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.binder.data.AnimeCarouselItem
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.databinding.SharedAnimeItemBinding

fun <T : Any>getWidgetItemBinder(
    dtoRetriever: (T) -> List<AnimeCarouselItem>,
    onItemClick: (AnimeItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedAnimeItemBinding, T> {

    override fun inflate(parent: ViewGroup) = SharedAnimeItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).apply {
        listItemCarousel.prepareList()
    }

    override fun bind(binder: SharedAnimeItemBinding, item: T) {
        with(binder) {
            animeList = dtoRetriever(item)
            executePendingBindings()
        }
    }

    private fun CustomRecyclerView.prepareList() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        setHasFixedSize(true)
        addItemBindings(viewHolders = getAnimeCarouselItemBinder(
            dtoRetriever = AnimeCarouselItem::dto,
            onItemClick = { onItemClick(it) }
        ))
    }
}