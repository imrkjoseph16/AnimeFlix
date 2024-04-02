package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.SectionTitleItemViewDto
import com.imrkjoseph.animenation.databinding.SharedSectionTitleItemBinding

data class SectionTitleItem(
    val id: Any? = null,
    val dto: SectionTitleItemViewDto
)

fun <T : Any>setupSectionTitleItemBinder(
    dtoRetriever: (T) -> SectionTitleItemViewDto,
    onSeeAllClick: (T) -> Unit = { }
) = object : RecyclerViewHolder<SharedSectionTitleItemBinding, T> {
    override fun bind(binder: SharedSectionTitleItemBinding, item: T) {
        with(binder) {
            dto = dtoRetriever(item)
            seeAll.setOnClickListener { onSeeAllClick(item) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = SharedSectionTitleItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}