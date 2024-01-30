package com.example.animenation.dashboard.shared.presentation.details

import android.os.Parcelable
import com.example.animenation.app.util.EntryPointType
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsArguments(
    val detailsId: String,
    val entryPointType: EntryPointType
) : Parcelable