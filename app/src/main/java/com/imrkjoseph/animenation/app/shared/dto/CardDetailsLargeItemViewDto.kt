package com.imrkjoseph.animenation.app.shared.dto

import com.imrkjoseph.animenation.app.util.Default.EntryPointType

/**
 * Reusable component
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.shared_card_details_large]
 * */
data class CardDetailsLargeItemViewDto(
    val itemId: String,
    val title: String? = null,
    var rating: Int? = 0,
    val itemType: String? = null,
    val itemStatus: String? = null,
    val itemDescription: String? = null,
    val itemImageUrl: String? = null,
    val itemEntryPoint: EntryPointType
) {

    fun getComputedRating() = ((rating?.times(10) ?: 0) / 100).toFloat()
}