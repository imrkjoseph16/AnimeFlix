package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.ExploreSeriesItemViewDto
import com.example.animenation.databinding.SharedExploreCarouselItemBinding

fun <T : Any> getExploreCarouselItemBinder(
    dtoRetriever: (T) -> ExploreSeriesItemViewDto,
    onItemClick: (ExploreSeriesItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedExploreCarouselItemBinding, T> {

    override fun bind(binder: SharedExploreCarouselItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            details = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedExploreCarouselItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}