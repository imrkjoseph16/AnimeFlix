package com.imrkjoseph.animenation.app.shared.dto

import android.content.Context
import com.imrkjoseph.animenation.app.component.TextLine
import com.imrkjoseph.animenation.app.util.EntryPointType

/**
 * Reusable component
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.shared_content_item]
 * */
data class ContentItemViewDto(
    private val itemTitle: TextLine = TextLine.EMPTY,
    private val itemDateSaved: TextLine = TextLine.EMPTY,
    private val itemSubtitle: TextLine = TextLine.EMPTY,
    val itemImageUrl: String? = null,
    val typeOfMovie: String? = null,
    val entryPointType: EntryPointType = EntryPointType.ALL,
    val itemId: String
) {
    fun getItemTitle(context: Context) = itemTitle.getString(context)
}