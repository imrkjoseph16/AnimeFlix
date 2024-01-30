package com.example.animenation.dashboard.shared.data.dto.anime

import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    @JsonProperty("episodeId")
    val episodeId: String? = null,

    @JsonProperty("episodeNumber")
    val episodeNumber: Int? = null,

    @JsonProperty("genres")
    val genres: List<String>? = null,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("subOrDub")
    val subOrDub: String? = null
)