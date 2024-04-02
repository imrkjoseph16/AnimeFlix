package com.imrkjoseph.animenation.dashboard.pages.explore.data

data class ExploreResultData(
    val currentPage: Int? = null,
    val hasNextPage: Boolean? = null,
    val totalResults: Int? = null,
    val resultDetails: List<ResultDetails>? = null
)

data class ResultDetails(
    val id: String,
    val title: String? = null,
    val urlLink: String? = null,
    val image: String? = null,
    val releaseDate: String? = null,
    val type: String? = null,
    val subOrDub: String? = null
)