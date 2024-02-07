package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.ContentItemViewDto
import com.imrkjoseph.animenation.databinding.SharedContentItemBinding

data class ContentItem(
    val id: Any? = null,
    val dto: ContentItemViewDto
)

fun <T : Any> setupContentItemBinder(
    dtoRetriever: (T) -> ContentItemViewDto,
    onItemClick: (ContentItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedContentItemBinding, T> {

    override fun bind(binder: SharedContentItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            content = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedContentItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}