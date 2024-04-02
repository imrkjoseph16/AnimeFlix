package com.imrkjoseph.animenation.app.shared.binder.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imrkjoseph.animenation.app.component.RecyclerViewHolder
import com.imrkjoseph.animenation.app.shared.dto.FavoritesItemViewDto
import com.imrkjoseph.animenation.databinding.FavoritesCategoryItemBinding

data class FavoritesItem(
    val id: Any? = null,
    val dto: FavoritesItemViewDto
)

fun <T : Any> setupFavoritesItemBinder(
    dtoRetriever: (T) -> FavoritesItemViewDto,
    onItemClick: (FavoritesItemViewDto) -> Unit
) = object : RecyclerViewHolder<FavoritesCategoryItemBinding, T> {

    override fun bind(binder: FavoritesCategoryItemBinding, item: T) {
        with(binder) {
            val itemDetails = dtoRetriever(item)
            data = itemDetails
            root.setOnClickListener { onItemClick(itemDetails) }
            executePendingBindings()
        }
    }

    override fun inflate(parent: ViewGroup) = FavoritesCategoryItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
}