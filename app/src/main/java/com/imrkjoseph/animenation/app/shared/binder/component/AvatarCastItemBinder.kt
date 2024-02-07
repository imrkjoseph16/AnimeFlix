package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.AvatarCastItemViewDto
import com.imrkjoseph.animenation.databinding.SharedAvatarCastItemBinding

fun setupAvatarCastItemBinder(
    onItemClick: (AvatarCastItemViewDto) -> Unit = { }
) = object : RecyclerViewHolder<SharedAvatarCastItemBinding, AvatarCastItemViewDto> {

    override fun bind(binder: SharedAvatarCastItemBinding, item: AvatarCastItemViewDto) {
        with(binder) {
            data = item
            root.setOnClickListener { onItemClick(item) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedAvatarCastItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}