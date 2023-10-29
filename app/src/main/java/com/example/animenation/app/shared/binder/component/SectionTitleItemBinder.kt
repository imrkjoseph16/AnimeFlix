package com.example.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.animenation.app.component.RecyclerViewHolder
import com.example.animenation.app.shared.dto.SectionTitleItemViewDto
import com.example.animenation.databinding.SharedSectionTitleItemBinding

val SectionTitleItemBinder = object :
    RecyclerViewHolder<SharedSectionTitleItemBinding, SectionTitleItemViewDto> {
    override fun bind(binder: SharedSectionTitleItemBinding, item: SectionTitleItemViewDto) {
        with(binder) {
            dto = item
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedSectionTitleItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}