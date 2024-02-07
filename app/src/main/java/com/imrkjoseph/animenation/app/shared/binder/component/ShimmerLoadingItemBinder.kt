package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.imrkjoseph.animenation.databinding.SharedShimmerAnimeItemBinding

val ShimmerLoadingItemBinder = object :
    RecyclerViewHolder<SharedShimmerAnimeItemBinding, ShimmerLoadingItemViewDto> {
    override fun bind(binder: SharedShimmerAnimeItemBinding, item: ShimmerLoadingItemViewDto) {
        with(binder) {
            shimmerAnime.startShimmerAnimation()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedShimmerAnimeItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}