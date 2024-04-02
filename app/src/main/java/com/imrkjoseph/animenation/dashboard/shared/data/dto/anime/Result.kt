package com.imrkjoseph.animenation.dashboard.shared.data.dto.anime

import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("malId")
    val malId: String? = null,

    @JsonProperty("title")
    val title: Title? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("episodeId")
    val episodeId: String? = null,

    @JsonProperty("episodeTitle")
    val episodeTitle: String? = null,

    @JsonProperty("episode")
    val episode: Int? = null,

    @JsonProperty("episodeNumber")
    val episodeNumber: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("color")
    val color: String? = null,

    @JsonProperty("trailer")
    val trailer: Trailer? = null,

    @JsonProperty("airingAt")
    val airingAt: Long? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("country")
    val country: String? = null,

    @JsonProperty("cover")
    val cover: String? = null,

    @JsonProperty("coverHash")
    val coverHash: String? = null,

    @JsonProperty("popularity")
    val popularity: Int? = null,

    @JsonProperty("rating")
    val rating: Int? = null,

    @JsonProperty("releaseDate")
    val releaseDate: Int? = null,

    @JsonProperty("genres")
    val genres: List<String>? = null,

    @JsonProperty("totalEpisodes")
    val totalEpisodes: Int? = null,

    @JsonProperty("currentEpisodeCount")
    val currentEpisodeCount: Int? = null,

    @JsonProperty("duration")
    val duration: Int? = null,

    @JsonProperty("url")
    val url: String? = null
)

data class Title(
    @JsonProperty("english")
    val english: String? = null,

    @JsonProperty("native")
    val native: String? = null,

    @JsonProperty("romaji")
    val romaji: String? = null,

    @JsonProperty("userPreferred")
    val userPreferred: String? = null
)

data class Trailer(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("site")
    val site: String? = null,

    @JsonProperty("thumbnail")
    val thumbnail: String? = null,

    @JsonProperty("thumbnailHash")
    val thumbnailHash: String? = null
)