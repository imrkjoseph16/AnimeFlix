package com.example.animenation.dashboard.shared.data.extension

import com.example.animenation.dashboard.shared.data.transformer.SharedTransformer
import com.example.animenation.dashboard.shared.data.ISearchAvailableAnime
import com.example.animenation.dashboard.shared.data.ISearchAvailableMovies
import com.example.animenation.dashboard.shared.data.ISearchExploreSeries

private val transformer = SharedTransformer()

fun ISearchAvailableAnime.transformAnime() = transformer.transformAnimeResponse(animeResponse = this)

fun ISearchAvailableMovies.transformMovies() = transformer.transformMoviesResponse(moviesResponse = this)

fun ISearchExploreSeries.transformExplore() = transformer.transformExploreResponse(exploreResponse = this)