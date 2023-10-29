package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.example.animenation.databinding.SharedShimmerAnimeItemBinding

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