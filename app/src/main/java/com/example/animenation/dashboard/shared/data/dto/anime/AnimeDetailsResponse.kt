package com.example.animenation.dashboard.shared.data.dto.anime

import com.fasterxml.jackson.annotation.JsonProperty

data class AnimeDetailsResponse(
    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("episodes")
    val episodes: List<Episode>? = null,

    @JsonProperty("genres")
    val genres: List<String>? = null,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("otherName")
    val otherName: String? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("subOrDub")
    val subOrDub: String? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("totalEpisodes")
    val totalEpisodes: Int = 0,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("url")
    val url: String? = null
)

data class Episode(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("number")
    val number: Int? = null,

    @JsonProperty("url")
    val url: String? = null
)