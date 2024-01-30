package com.example.animenation.dashboard.shared.data.dto.movies

import com.example.animenation.dashboard.shared.data.ISearchAvailableMovies
import com.fasterxml.jackson.annotation.JsonProperty

data class MoviesResponse(
    @JsonProperty("currentPage")
    override val currentPage: Int,

    @JsonProperty("hasNextPage")
    override val hasNextPage: Boolean,

    @JsonProperty("results")
    override val results: List<Result>? = null
) : ISearchAvailableMovies