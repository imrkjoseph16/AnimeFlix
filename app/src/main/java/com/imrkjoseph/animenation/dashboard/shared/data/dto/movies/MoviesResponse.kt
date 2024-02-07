package com.imrkjoseph.animenation.dashboard.shared.data.dto.movies

import com.fasterxml.jackson.annotation.JsonProperty

data class MoviesResponse(
    @JsonProperty("currentPage")
    val currentPage: Int? = null,

    @JsonProperty("hasNextPage")
    val hasNextPage: Boolean? = null,

    @JsonProperty("results")
    val results: List<Result>? = null,

    @JsonProperty("totalPages")
    val totalPages: Int? = null,

    @JsonProperty("totalResults")
    val totalResults: Int? = null
)

data class Result(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("rating")
    val rating: Int? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("type")
    val type: String? = null
)