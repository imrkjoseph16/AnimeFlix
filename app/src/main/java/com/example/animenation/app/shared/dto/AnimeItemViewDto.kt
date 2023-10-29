package com.example.animenation.app.shared.dto

import android.content.Context
import com.example.animenation.app.component.TextLine

/**
 * Reusable component for notes list item.
 *
 * Describes data rendered in [com.example.animenation.R.layout.shared_anime_item]
 * */
data class AnimeItemViewDto(
    private val itemTitle: TextLine = TextLine.EMPTY,
    private val itemDateSaved: TextLine = TextLine.EMPTY,
    private val itemSubtitle: TextLine = TextLine.EMPTY,
    val itemImageUrl: String? = null,
    val itemId: String,
) {
    fun getItemTitle(context: Context) = itemTitle.getString(context)
}