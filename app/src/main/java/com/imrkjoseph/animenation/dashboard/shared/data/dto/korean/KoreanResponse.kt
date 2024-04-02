package com.imrkjoseph.animenation.dashboard.shared.data.dto.korean

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KoreanResponse(
    @JsonProperty("currentPage")
    val currentPage: Int,

    @JsonProperty("hasNextPage")
    val hasNextPage: Boolean,

    @JsonProperty("totalResults")
    val totalResults: Int? = null,

    @JsonProperty("results")
    val results: List<Result>? = null
)