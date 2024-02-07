package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import android.os.Parcelable
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoStreamingArguments(
    val episodeId: String,
    val showId: String? = null,
    val details: DetailsFullData? = null,
    val entryPointType: EntryPointType,
) : Parcelable