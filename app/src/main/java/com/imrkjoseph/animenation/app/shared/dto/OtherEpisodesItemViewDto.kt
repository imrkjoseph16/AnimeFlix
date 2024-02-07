package com.imrkjoseph.animenation.app.shared.dto

import android.content.Context
import com.imrkjoseph.animenation.app.component.TextLine

/**
 * Reusable component
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.shared_other_episodes_item]
 * */
data class OtherEpisodesItemViewDto(
    val itemTitle: TextLine = TextLine.EMPTY,
    val itemSubtitle: TextLine = TextLine.EMPTY,
    val itemDescription: TextLine = TextLine.EMPTY,
    val itemImageUrl: String? = null,
    val itemShowId: String? = null,
    val itemEpisodeId: String
) {
    fun getItemTitle(context: Context) = itemTitle.getString(context)

    fun getItemSubtitle(context: Context) = itemSubtitle.getString(context)

    fun getItemDescription(context: Context) = itemDescription.getString(context)
}