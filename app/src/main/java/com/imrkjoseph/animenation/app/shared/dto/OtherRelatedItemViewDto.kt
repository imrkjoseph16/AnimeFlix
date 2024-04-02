package com.imrkjoseph.animenation.app.shared.dto

import com.imrkjoseph.animenation.app.util.EntryPointType

/**
 * Reusable component
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.shared_other_related_item]
 * */
data class OtherRelatedItemViewDto(
    val title: String? = null,
    var rating: Int? = 0,
    val itemType: String? = null,
    val itemStatus: String? = null,
    val itemDescription: String? = null,
    val itemImageUrl: String? = null,
    val itemId: String,
    val itemEntryPointType: EntryPointType
) {

    fun getComputedRating() = ((rating?.times(10) ?: 0) / 100).toFloat()
}