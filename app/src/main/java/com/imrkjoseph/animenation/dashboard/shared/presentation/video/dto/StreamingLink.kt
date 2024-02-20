package com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StreamingLink(
    @JsonProperty("download")
    val download: String? = null,

    @JsonProperty("headers")
    val headers: Headers? = null,

    @JsonProperty("sources")
    val sources: List<Source>? = null,

    @JsonProperty("subtitles")
    val subtitles: List<Subtitle>? = null
)

data class Headers(
    @JsonProperty("Referer")
    val referer: String? = null
)

data class Source(
    @JsonProperty("isM3U8")
    val isM3U8: Boolean? = null,

    @JsonProperty("quality")
    val quality: String? = null,

    @JsonProperty("url")
    val url: String
)

data class Subtitle(
    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("lang")
    val lang: String? = null
)