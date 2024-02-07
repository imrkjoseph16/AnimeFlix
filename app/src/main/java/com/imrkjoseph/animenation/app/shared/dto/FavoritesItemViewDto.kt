package com.imrkjoseph.animenation.app.shared.dto

/**
 * Reusable component
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.favorites_category_item]
 * */
class FavoritesItemViewDto(
    val itemTitle: String? = null,
    val itemDate: String? = null,
    val itemImageUrl: String? = null,
    val itemId: String,
    val type: String
)