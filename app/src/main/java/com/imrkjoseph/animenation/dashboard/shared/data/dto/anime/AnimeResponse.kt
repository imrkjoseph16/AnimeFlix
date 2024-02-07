package com.imrkjoseph.animenation.dashboard.shared.data.dto.anime

import com.fasterxml.jackson.annotation.JsonProperty

data class AnimeResponse(
    @JsonProperty("currentPage")
    val currentPage: Int,

    @JsonProperty("hasNextPage")
    val hasNextPage: Boolean? = null,

    @JsonProperty("totalResults")
    val totalResults: Int,

    @JsonProperty("results")
    val results: List<Result>? = null
)