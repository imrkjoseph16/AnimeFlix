package com.example.animenation.dashboard.shared.data.dto.movies

import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("image")
    val image: String,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("url")
    val url: String,

    @JsonProperty("releaseDate")
    val releaseDate: String? = null,

    @JsonProperty("type")
    val type: String? = null
)