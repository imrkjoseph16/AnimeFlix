package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.component.ListItemPayloadDiffCallback
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.ContentItemViewDto
import com.imrkjoseph.animenation.databinding.SharedHorizontalListItemBinding

data class HorizontalListContentItem(
    val id: Any? = null,
    val payload: List<Any>
)

fun setupHorizontalListItemBinder(
    onItemClick: (ContentItemViewDto) -> Unit = { },
    onLargeCardClick: (CardDetailsLargeItem) -> Unit = { }
) = object : RecyclerViewHolder<SharedHorizontalListItemBinding, HorizontalListContentItem> {

    override fun inflate(parent: ViewGroup) = SharedHorizontalListItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).apply {
        horizontalItemList.prepareList()
    }

    override fun bind(binder: SharedHorizontalListItemBinding, item: HorizontalListContentItem) {
        with(binder) {
            contentList = item.payload
            executePendingBindings()
        }
    }

    private fun CustomRecyclerView.prepareList() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        setHasFixedSize(true)
        addItemBindings(viewHolders = setupContentItemBinder(
            dtoRetriever = ContentItem::dto,
            onItemClick = { onItemClick(it) }
        ))
        addItemBindings(viewHolders = setupCardDetailsLargeItemBinder(
            dtoRetriever = CardDetailsLargeItem::dto,
            onItemClick = { onLargeCardClick(it) }
        ))
        addItemBindings(viewHolders = setupAvatarCastItemBinder())
    }
}