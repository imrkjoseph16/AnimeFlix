package com.example.animenation.app.shared.dto

import android.content.Context
import com.example.animenation.app.component.TextLine

/**
 * Reusable component
 *
 * Describes data rendered in [com.example.animenation.R.layout.shared_explore_carousel_item]
 * */
data class ExploreSeriesItemViewDto(
    private val itemTitle: TextLine = TextLine.EMPTY,
    val itemImageUrl: String? = null,
    val itemId: String,
) {

    fun getItemTitle(context: Context) = itemTitle.getString(context)
}