package com.example.animenation.dashboard.shared.data.dto.anime

import com.example.animenation.dashboard.shared.data.ISearchAvailableAnime
import com.fasterxml.jackson.annotation.JsonProperty

data class AnimeResponse(
    @JsonProperty("currentPage")
    override val currentPage: Int,

    @JsonProperty("hasNextPage")
    override val hasNextPage: Boolean,

    @JsonProperty("results")
    override val results: List<Result>? = null
) : ISearchAvailableAnime