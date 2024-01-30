package com.example.animenation.app.shared.dto

import android.content.Context
import com.example.animenation.app.component.TextLine

/**
 * Reusable component
 *
 * Describes data rendered in [com.example.animenation.R.layout.shared_other_episodes_item]
 * */
data class OtherEpisodesItemViewDto(
    private val itemTitle: TextLine = TextLine.EMPTY,
    private val itemSubtitle: TextLine = TextLine.EMPTY,
    val episodeUrl: String? = null,
    val itemImageUrl: String? = null,
    val itemId: String,
) {
    fun getItemTitle(context: Context) = itemTitle.getString(context)

    fun getItemSubtitle(context: Context) = itemSubtitle.getString(context)
}