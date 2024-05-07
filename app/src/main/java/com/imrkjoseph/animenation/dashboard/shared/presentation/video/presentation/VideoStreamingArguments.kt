package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import android.os.Parcelable
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoStreamingArguments(
    val detailsId: String,
    val typeOfMovie: String? = null,
    val episodeId: String,
    val showId: String? = null,
    val entryPointType: EntryPointType,
    val selectedEpisode: Int = 1
) : Parcelable