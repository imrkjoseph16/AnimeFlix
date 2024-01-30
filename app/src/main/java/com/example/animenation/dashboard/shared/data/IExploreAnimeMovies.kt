package com.example.animenation.dashboard.shared.data

import com.example.animenation.dashboard.shared.data.dto.anime.Result
import com.example.animenation.dashboard.shared.data.dto.movies.Result as MovieResult

interface IExploreAnimeMovies {
    fun searchSuccess() : Boolean
}

interface ISearchAvailableAnime : IExploreAnimeMovies {
     val currentPage: Int
     val hasNextPage: Boolean
     val results: List<Result>?

    override fun searchSuccess() = results?.isNotEmpty() == true
}

interface ISearchAvailableMovies : IExploreAnimeMovies {
    val currentPage: Int
    val hasNextPage: Boolean
    val results: List<MovieResult>?

    override fun searchSuccess() = results?.isNotEmpty() == true
}

interface ISearchExploreSeries : IExploreAnimeMovies {
    val currentPage: Int
    val hasNextPage: Boolean
    val results: List<MovieResult>?

    override fun searchSuccess() = results?.isNotEmpty() == true
}