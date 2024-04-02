package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.LoadingItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.imrkjoseph.animenation.databinding.SharedLoadingItemBinding
import com.imrkjoseph.animenation.databinding.SharedShimmerAnimeItemBinding

val LoadingItemBinder = object :
    RecyclerViewHolder<SharedLoadingItemBinding, LoadingItemViewDto> {
    override fun bind(binder: SharedLoadingItemBinding, item: LoadingItemViewDto) = Unit

    override fun inflate(parent: ViewGroup) = SharedLoadingItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}