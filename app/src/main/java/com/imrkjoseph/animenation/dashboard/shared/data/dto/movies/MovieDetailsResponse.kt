package com.imrkjoseph.animenation.dashboard.shared.data.dto.movies

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MovieDetailsResponse(
    @JsonProperty("actors")
    val actors: List<String>? = null,

    @JsonProperty("cover")
    val cover: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("directors")
    val directors: List<String>? = null,

    @JsonProperty("duration")
    val duration: Int? = null,

    @JsonProperty("totalEpisodes")
    val totalEpisodes: Int? = null,

    @JsonProperty("totalSeasons")
    val totalSeasons: Int? = null,

    @JsonProperty("episodeId")
    val episodeId: String? = null,

    @JsonProperty("genres")
    val genres: List<String>? = null,

    @JsonProperty("id")
    val showId: String,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("logos")
    val logos: List<Logos>? = null,

    @JsonProperty("mappings")
    val mappings: Mappings? = null,

    @JsonProperty("rating")
    val rating: Int? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("trailer")
    val trailer: Trailer? = null,

    @JsonProperty("translations")
    val translations: List<Translation>? = null,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("writers")
    val writers: List<String>? = null,

    @JsonProperty("similar")
    val similar: List<SimilarMovies>? = null,

    @JsonProperty("recommendations")
    val recommendations: List<SimilarMovies>? = null,

    @JsonProperty("seasons")
    val seasons: List<SeasonEpisodes>? = null
)

data class Mappings(
    @JsonProperty("imdb")
    val imdb: String? = null,

    @JsonProperty("tmdb")
    val tmdb: Int? = null
)

data class Trailer(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("site")
    val site: String? = null
)

data class Translation(
    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("language")
    val language: String? = null,

    @JsonProperty("title")
    val title: String? = null
)

class Logos(
    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("aspectRatio")
    val aspectRatio: Double? = null,

    @JsonProperty("width")
    val width: Int? = null
)

class SimilarMovies(
    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("rating")
    val rating: Double? = null,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null
)