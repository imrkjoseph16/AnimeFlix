package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsLargeItemViewDto
import com.imrkjoseph.animenation.databinding.SharedCardDetailsLargeBinding

data class CardDetailsLargeItem(
    val id: Any? = null,
    val dto: CardDetailsLargeItemViewDto
)

fun<T: Any> setupCardDetailsLargeItemBinder(
    dtoRetriever: (T) -> CardDetailsLargeItemViewDto,
    onItemClick: (T) -> Unit = { }
) = object : RecyclerViewHolder<SharedCardDetailsLargeBinding, T> {

    override fun bind(binder: SharedCardDetailsLargeBinding, item: T) {
        with(binder) {
            data = dtoRetriever(item)
            detailsImage.setOnClickListener { onItemClick(item) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedCardDetailsLargeBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}