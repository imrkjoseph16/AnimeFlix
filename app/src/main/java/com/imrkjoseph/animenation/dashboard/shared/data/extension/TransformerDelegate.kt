package com.imrkjoseph.animenation.dashboard.shared.data.extension

import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.korean.KoreanDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.korean.KoreanResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.movies.MovieDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.movies.MoviesResponse
import com.imrkjoseph.animenation.dashboard.shared.data.transformer.SharedTransformer
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.FileMoonStreamLink

private val transformer = SharedTransformer()

fun AnimeResponse.transformAnime() = transformer.transformAnimeResponse(animeResponse = this)

fun KoreanResponse.transformKorean() = transformer.transformKoreanResponse(koreanResponse = this)

fun MoviesResponse.transformMovies() = transformer.transformMoviesResponse(moviesResponse = this)

fun AnimeDetailsResponse.transformAnimeDetails() = transformer.transformAnimeDetailsResponse(response = this)

fun KoreanDetailsResponse.transformSeriesDetails() = transformer.transformKoreanDetailsResponse(response = this)

fun MovieDetailsResponse.transformMovieDetails() = transformer.transformMovieDetailsResponse(response = this)

fun FileMoonStreamLink.transformAlternativeMovieStream() = transformer.transformAlternativeStreamResponse(response = this)