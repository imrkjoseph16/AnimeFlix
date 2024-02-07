package com.imrkjoseph.animenation.dashboard.shared.data.dto.anime

import com.fasterxml.jackson.annotation.JsonProperty

data class AnimeDetailsResponse(
    @JsonProperty("artwork")
    val artwork: List<Artwork>? = null,

    @JsonProperty("characters")
    val characters: List<Character>? = null,

    @JsonProperty("color")
    val color: String? = null,

    @JsonProperty("countryOfOrigin")
    val countryOfOrigin: String? = null,

    @JsonProperty("cover")
    val cover: String? = null,

    @JsonProperty("coverHash")
    val coverHash: String? = null,

    @JsonProperty("currentEpisode")
    val currentEpisode: Int? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("duration")
    val duration: Int? = null,

    @JsonProperty("endDate")
    val endDate: EndDate? = null,

    @JsonProperty("nextAiringEpisode")
    val nextAiringEpisode: NextAiringEpisode? = null,

    @JsonProperty("episodes")
    val episodes: List<Episode>? = null,

    @JsonProperty("genres")
    val genres: List<String>? = null,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("otherName")
    val otherName: String? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("isAdult")
    val isAdult: Boolean? = null,

    @JsonProperty("isLicensed")
    val isLicensed: Boolean? = null,

    @JsonProperty("malId")
    val malId: Int? = null,

    @JsonProperty("mappings")
    val mappings: List<Mapping>? = null,

    @JsonProperty("popularity")
    val popularity: Int? = null,

    @JsonProperty("rating")
    val rating: Int? = null,

    @JsonProperty("recommendations")
    val recommendations: List<Recommendations>? = null,

    @JsonProperty("relations")
    val relations: List<Relation>? = null,

    @JsonProperty("releaseDate")
    val releaseDate: Int? = null,

    @JsonProperty("season")
    val season: String? = null,

    @JsonProperty("startDate")
    val startDate: StartDate? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("studios")
    val studios: List<String>? = null,

    @JsonProperty("subOrDub")
    val subOrDub: String? = null,

    @JsonProperty("synonyms")
    val synonyms: List<String>? = null,

    @JsonProperty("title")
    val title: Title? = null,

    @JsonProperty("totalEpisodes")
    val totalEpisodes: Int = 0,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("trailer")
    val trailer: Trailer? = null,
)

data class Artwork(
    @JsonProperty("img")
    val img: String? = null,

    @JsonProperty("providerId")
    val providerId: String? = null,

    @JsonProperty("type")
    val type: String? = null
)

data class Character(
    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("name")
    val name: Name? = null,

    @JsonProperty("role")
    val role: String? = null,

    @JsonProperty("voiceActors")
    val voiceActors: List<VoiceActor>? = emptyList()
)

data class Episode(
    @JsonProperty("airDate")
    val airDate: String? = null,

    @JsonProperty("createdAt")
    val createdAt: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("number")
    val number: Int? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("url")
    val url: String? = null
)

data class Mapping(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("providerId")
    val providerId: String? = null,

    @JsonProperty("providerType")
    val providerType: String? = null,

    @JsonProperty("similarity")
    val similarity: Int? = null
)

data class Relation(
    @JsonProperty("color")
    val color: String? = null,

    @JsonProperty("cover")
    val cover: String? = null,

    @JsonProperty("coverHash")
    val coverHash: String? = null,

    @JsonProperty("episodes")
    val totalEpisodes: Int? = null,

    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("malId")
    val malId: Int? = null,

    @JsonProperty("rating")
    val rating: Int? = null,

    @JsonProperty("relationType")
    val relationType: String? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("title")
    val title: Title? = null,

    @JsonProperty("type")
    val type: String? = null
)

data class StartDate(
    @JsonProperty("day")
    val day: Int? = null,

    @JsonProperty("month")
    val month: Int? = null,

    @JsonProperty("year")
    val year: Int? = null
)

data class EndDate(
    @JsonProperty("day")
    val day: Int? = null,

    @JsonProperty("month")
    val month: Int? = null,

    @JsonProperty("year")
    val year: Int? = null
)

data class Name(
    @JsonProperty("first")
    val first: String? = null,

    @JsonProperty("full")
    val full: String? = null,

    @JsonProperty("last")
    val last: String? = null,

    @JsonProperty("native")
    val native: String? = null,

    @JsonProperty("userPreferred")
    val userPreferred: String? = null
)

data class VoiceActor(
    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("language")
    val language: String? = null,

    @JsonProperty("name")
    val name: Name? = null
)

data class Recommendations(
    @JsonProperty("cover")
    val cover: String? = null,

    @JsonProperty("coverHash")
    val coverHash: String? = null,

    @JsonProperty("episodes")
    val episodes: Int? = null,

    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("imageHash")
    val imageHash: String? = null,

    @JsonProperty("malId")
    val malId: Int? = null,

    @JsonProperty("rating")
    val rating: Int? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("title")
    val title: Title? = null,

    @JsonProperty("type")
    val type: String? = null
)

data class NextAiringEpisode(
    @JsonProperty("airingTime")
    val airingTime: Int? = null,

    @JsonProperty("timeUntilAiring")
    val timeUntilAiring: Int? = null,

    @JsonProperty("episode")
    val episode: Int? = null
)