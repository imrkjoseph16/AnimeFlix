package com.example.animenation.app.shared.dto

import android.content.Context
import com.example.animenation.app.component.TextLine

/**
 * Reusable component
 *
 * Describes data rendered in [com.example.animenation.R.layout.shared_search_anime_item]
 * */
data class SearchAnimeItemViewDto(
    private val itemTitle: TextLine = TextLine.EMPTY,
    private val itemReleasedDate: TextLine = TextLine.EMPTY,
    private val itemTag: TextLine = TextLine.EMPTY,
    val itemImageUrl: String? = null,
    val itemUrl: String? = null,
    val itemId: String,
) {

    fun getItemTitle(context: Context) = itemTitle.getString(context)

    fun getItemReleaseDate(context: Context) = itemReleasedDate.getString(context)

    fun getItemTag(context: Context) = itemTag.getString(context)
}