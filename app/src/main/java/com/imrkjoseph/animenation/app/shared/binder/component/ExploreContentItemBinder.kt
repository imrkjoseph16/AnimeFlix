package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.ExploreContentItemViewDto
import com.imrkjoseph.animenation.databinding.SharedExploreContentItemBinding

data class ExploreContentItem(
    val id: Any? = null,
    val dto: ExploreContentItemViewDto
)

fun <T : Any> setupExploreContentsItemBinder(
    dtoRetriever: (T) -> ExploreContentItemViewDto,
    onItemClick: (ExploreContentItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedExploreContentItemBinding, T> {

    override fun bind(binder: SharedExploreContentItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            details = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedExploreContentItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}