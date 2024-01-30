package com.example.animenation.dashboard.pages.explore.data

data class ExploreResultData(
    val currentPage: Int? = null,
    val hasNextPage: Boolean? = null,
    val resultDetails: List<ResultDetails>? = emptyList()
)

data class ResultDetails(
    val id: String,
    val title: String,
    val urlLink: String,
    val image: String,
    val releaseDate: String? = null,
    val type: String? = null,
    val subOrDub: String? = null
)