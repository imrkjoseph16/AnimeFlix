package com.example.animenation.dashboard.shared.data.dto.movies

import com.fasterxml.jackson.annotation.JsonProperty

data class SeriesDetailsResponse(
    @JsonProperty("casts")
    val casts: List<String>? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("duration")
    val duration: String? = null,

    @JsonProperty("otherNames")
    val otherNames: List<String>? = null,

    @JsonProperty("episodes")
    val episodes: List<Episode>? = null,

    @JsonProperty("genres")
    val genres: List<String>? = null,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("production")
    val production: String? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("tags")
    val tags: List<String>? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("url")
    val url: String? = null
)

data class Episode(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("number")
    val number: Int? = null,

    @JsonProperty("episode")
    val episode: Int? = null,

    @JsonProperty("season")
    val season: Int? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("subType")
    val subType: String? = null
)