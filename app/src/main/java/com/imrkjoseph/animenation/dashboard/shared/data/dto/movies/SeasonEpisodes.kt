package com.imrkjoseph.animenation.dashboard.shared.data.dto.movies

import com.fasterxml.jackson.annotation.JsonProperty

data class SeasonEpisodes(
    @JsonProperty("episodes")
    val episodes: List<Episode>? = null,

    @JsonProperty("image")
    val image: Image? = null,

    @JsonProperty("isReleased")
    val isReleased: Boolean? = null,

    @JsonProperty("season")
    val season: Int? = null
)

data class Image(
    @JsonProperty("hd")
    val hd: String? = null,

    @JsonProperty("mobile")
    val mobile: String? = null
)

data class Episode(
    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("episode")
    val episode: Int? = null,

    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("img")
    val img: Img? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("season")
    val season: Int? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("url")
    val url: String? = null
)

data class Img(
    @JsonProperty("hd")
    val hd: String? = null,

    @JsonProperty("mobile")
    val mobile: String? = null
)