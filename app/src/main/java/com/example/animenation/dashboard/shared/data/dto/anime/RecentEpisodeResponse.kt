package com.example.animenation.dashboard.shared.data.dto.anime

import com.fasterxml.jackson.annotation.JsonProperty

data class RecentEpisodeResponse(
    @JsonProperty("currentPage")
    val currentPage: Int,

    @JsonProperty("hasNextPage")
    val hasNextPage: Boolean,

    @JsonProperty("results")
    val results: List<Result>? = null
)