package com.example.animenation.dashboard.shared.presentation.video.presentation

import android.os.Parcelable
import com.example.animenation.app.util.EntryPointType
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoStreamingArguments(
    val episodeId: String,
    val entryPointType: EntryPointType
) : Parcelable