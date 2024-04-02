package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.component.ListItemPayloadDiffCallback
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.ExploreContentItemViewDto
import com.imrkjoseph.animenation.databinding.SharedVerticalListItemBinding

data class VerticalListContentItem(
    val id: Any? = null,
    val payload: List<Any>
)

fun setupVerticalListItemBinder(
    onItemClick: (ExploreContentItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedVerticalListItemBinding, VerticalListContentItem> {

    override fun inflate(parent: ViewGroup) = SharedVerticalListItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).apply {
        verticalItemList.prepareList()
    }

    override fun bind(binder: SharedVerticalListItemBinding, item: VerticalListContentItem) {
        with(binder) {
            itemList = item.payload
            executePendingBindings()
        }
    }

    private fun CustomRecyclerView.prepareList() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        setHasFixedSize(true)
        addItemBindings(viewHolders = setupExploreContentsItemBinder(
            dtoRetriever = ExploreContentItem::dto,
            onItemClick = { onItemClick(it) }
        ))
    }
}