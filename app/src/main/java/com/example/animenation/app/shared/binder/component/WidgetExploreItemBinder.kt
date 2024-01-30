package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.binder.data.ExploreCarouselItem
import com.example.animenation.app.shared.dto.ExploreSeriesItemViewDto
import com.example.animenation.databinding.SharedExploreItemBinding

fun <T : Any>getWidgetExploreItemBinder(
    dtoRetriever: (T) -> List<ExploreCarouselItem>,
    onItemClick: (ExploreSeriesItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedExploreItemBinding, T> {

    override fun inflate(parent: ViewGroup) = SharedExploreItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).apply {
        listItemCarousel.prepareList()
    }

    override fun bind(binder: SharedExploreItemBinding, item: T) {
        with(binder) {
            itemList = dtoRetriever(item)
            executePendingBindings()
        }
    }

    private fun CustomRecyclerView.prepareList() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        setHasFixedSize(true)
        addItemBindings(viewHolders = getExploreCarouselItemBinder(
            dtoRetriever = ExploreCarouselItem::dto,
            onItemClick = { onItemClick(it) }
        ))
    }
}