package com.example.animenation.dashboard.shared.presentation.details.data

data class DetailsFullData(
    val description: String? = null,
    val casts: List<String>? = emptyList(),
    val episodes: List<Episode>? = emptyList(),
    val tags: List<String>? = emptyList(),
    val genres: List<String>? = null,
    val id: String,
    val duration: String? = null,
    val production: String? = null,
    val image: String? = null,
    val otherName: String? = null,
    val otherNames: List<String>? = null,
    val releaseDate: String? = null,
    val status: String? = null,
    val subOrDub: String? = null,
    val title: String? = null,
    val totalEpisodes: Int = 0,
    val type: String? = null,
    val url: String? = null
)

data class Episode(
    val id: String? = null,
    val episode: Int? = null,
    val subType: String? = null,
    val number: Int? = null,
    val url: String? = null,
    val season: Int? = null,
    val title: String? = null,
    val releaseDate: String? = null
)
