package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.OtherEpisodesItemViewDto
import com.imrkjoseph.animenation.databinding.SharedOtherEpisodesItemBinding

fun setupOtherEpisodesItemBinder(
    onItemClick: (OtherEpisodesItemViewDto) -> Unit
) = object : RecyclerViewHolder<SharedOtherEpisodesItemBinding, OtherEpisodesItemViewDto> {

    override fun bind(binder: SharedOtherEpisodesItemBinding, item: OtherEpisodesItemViewDto) {
        with(binder) {
            episodes = item
            root.setOnClickListener { onItemClick(item) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedOtherEpisodesItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}