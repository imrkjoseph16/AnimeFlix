package com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto

import com.google.gson.annotations.SerializedName

data class FileMoonStreamLink(
    @SerializedName("data")
    val data: StreamData? = null,

    @SerializedName("name")
    val name: String? = null,
)

data class StreamData(
    @SerializedName("file")
    val file: String? = null,

    @SerializedName("sub")
    val sub: List<Subtitles>? = null
)

data class Subtitles(
    @SerializedName("file")
    val file: String? = null,

    @SerializedName("lang")
    val lang: String? = null
)