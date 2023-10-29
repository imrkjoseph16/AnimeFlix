package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.binder.data.AnimeCarouselItem
import com.example.animenation.databinding.SharedAnimeItemBinding

fun <T : Any>getMovieItemBinder(
    dtoRetriever: (T) -> List<AnimeCarouselItem>
) = object : RecyclerViewHolder<SharedAnimeItemBinding, T> {

    override fun inflate(parent: ViewGroup) = SharedAnimeItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).apply {
        listMoviesCarousel.prepareSuggestionsList()
    }

    override fun bind(binder: SharedAnimeItemBinding, item: T) {
        with(binder) {
            animeList = dtoRetriever(item)
            executePendingBindings()
        }
    }

    private fun CustomRecyclerView.prepareSuggestionsList() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        setHasFixedSize(true)
        addItemBindings(viewHolders = getMovieCarouselItemBinder(dtoRetriever = AnimeCarouselItem::dto))
    }
}