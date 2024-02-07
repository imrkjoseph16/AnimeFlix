package com.imrkjoseph.animenation.app.shared.dto

import android.content.Context
import com.imrkjoseph.animenation.app.component.TextLine

/**
 * Reusable component
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.shared_section_title_item]
 * */
data class SectionTitleItemViewDto(
    private val leftTitle: TextLine = TextLine.EMPTY,
    private val rightTitle: TextLine = TextLine.EMPTY,
) {

    fun getLeftTitle(context: Context) = leftTitle.getString(context)

    fun getRightTitle(context: Context) = rightTitle.getString(context)
}