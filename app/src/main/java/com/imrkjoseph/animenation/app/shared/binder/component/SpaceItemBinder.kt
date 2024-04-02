package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.SpaceItemViewDto
import com.imrkjoseph.animenation.databinding.SharedListSpaceItemBinding

val SpaceItemViewDtoBinder = object :
    RecyclerViewHolder<SharedListSpaceItemBinding, SpaceItemViewDto> {
    override fun bind(binder: SharedListSpaceItemBinding, item: SpaceItemViewDto) {
        with(binder) {
            data = item
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedListSpaceItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}