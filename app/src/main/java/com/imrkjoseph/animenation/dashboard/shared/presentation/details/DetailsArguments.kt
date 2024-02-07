package com.imrkjoseph.animenation.dashboard.shared.presentation.details

import android.os.Parcelable
import com.imrkjoseph.animenation.app.util.EntryPointType
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsArguments(
    val detailsId: String,
    val typeOfMovie: String? = null,
    val entryPointType: EntryPointType,
) : Parcelable