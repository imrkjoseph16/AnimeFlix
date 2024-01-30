package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.LoadingItemViewDto
import com.example.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.example.animenation.databinding.SharedLoadingItemBinding
import com.example.animenation.databinding.SharedShimmerAnimeItemBinding

val LoadingItemBinder = object :
    RecyclerViewHolder<SharedLoadingItemBinding, LoadingItemViewDto> {
    override fun bind(binder: SharedLoadingItemBinding, item: LoadingItemViewDto) = Unit

    override fun inflate(parent: ViewGroup) = SharedLoadingItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}