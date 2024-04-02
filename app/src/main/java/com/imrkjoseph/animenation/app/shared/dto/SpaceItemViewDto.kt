package com.imrkjoseph.animenation.app.shared.dto

import androidx.annotation.DimenRes
import com.imrkjoseph.animenation.R

/**
 * Reusable component for empty space on the screen
 *
 * Describes data rendered in [com.imrkjoseph.animenation.R.layout.shared_list_space_item]
 * */
data class SpaceItemViewDto(@DimenRes val heightDimenRes: Int = R.dimen.grid_0)