package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.OtherRelatedItemViewDto
import com.imrkjoseph.animenation.databinding.SharedOtherRelatedItemBinding

fun setupOtherRelatedItemBinder(
    onItemClick: (OtherRelatedItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedOtherRelatedItemBinding, OtherRelatedItemViewDto> {

    override fun bind(binder: SharedOtherRelatedItemBinding, item: OtherRelatedItemViewDto) {
        with(binder) {
            data = item
            root.setOnClickListener { onItemClick(item) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedOtherRelatedItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}