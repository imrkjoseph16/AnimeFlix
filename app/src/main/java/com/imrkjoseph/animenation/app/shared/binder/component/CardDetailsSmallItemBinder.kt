package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsSmallItemViewDto
import com.imrkjoseph.animenation.databinding.SharedCardDetailsSmallBinding

data class CardDetailsSmallItem(
    val id: Any? = null,
    val dto: CardDetailsSmallItemViewDto
)

fun<T: Any> setupCardDetailsSmallItemBinder(
    dtoRetriever: (T) -> CardDetailsSmallItemViewDto,
    onItemClick: (T) -> Unit = { }
) = object : RecyclerViewHolder<SharedCardDetailsSmallBinding, T> {

    override fun bind(binder: SharedCardDetailsSmallBinding, item: T) {
        with(binder) {
            data = dtoRetriever(item)
            root.setOnClickListener { onItemClick(item) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedCardDetailsSmallBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}