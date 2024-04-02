package com.imrkjoseph.animenation.dashboard.shared.presentation.details.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsFullData(
    val description: String? = null,
    val casts: List<String> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val tags: List<String>? = emptyList(),
    val genres: List<String>? = null,
    val id: String,
    val episodeId: String? = null,
    val duration: String? = null,
    val production: String? = null,
    val cover: String? = null,
    val image: String? = null,
    val otherName: String? = null,
    val synonyms: List<String>? = null,
    val otherNames: List<String>? = null,
    val releaseDate: String? = null,
    val status: String? = null,
    val subOrDub: String? = null,
    val title: String? = null,
    val totalEpisodes: Int = 0,
    val type: String? = null,
    val season: String? = null,
    val characters: List<Character> = emptyList(),
    val recommendations: List<Episode> = emptyList(),
    val relations: List<Episode> = emptyList(),
    val url: String? = null
) : Parcelable

@Parcelize
data class Character(
    val id: Int? = null,
    val image: String? = null,
    val name: Name? = null,
    val role: String? = null,
    val voiceActors: List<VoiceActor>? = emptyList()
) : Parcelable

@Parcelize
data class VoiceActor(
    val id: Int? = null,
    val image: String? = null,
    val language: String? = null,
    val name: Name? = null
) : Parcelable

@Parcelize
data class Episode(
    val showId: String? = null,
    val episodeId: String,
    val episode: Int? = null,
    val subType: String? = null,
    val title: Title? = null,
    val description: String? = null,
    val number: Int? = null,
    val image: String? = null,
    val imageHash: String? = null,
    val airDate: String? = null,
    val createdAt: String? = null,
    val url: String? = null,
    val season: Int? = null,
    val releaseDate: String? = null,
    val color: String? = null,
    val cover: String? = null,
    val totalEpisodes: Int? = null,
    val rating: Int? = null,
    val relationType: String? = null,
    val status: String? = null,
    val type: String? = null
) : Parcelable

@Parcelize
data class Title(
    val english: String? = null,
    val native: String? = null,
) : Parcelable

@Parcelize
data class Name(
    val first: String? = null,
    val full: String? = null,
    val last: String? = null,
    val native: String? = null,
) : Parcelable