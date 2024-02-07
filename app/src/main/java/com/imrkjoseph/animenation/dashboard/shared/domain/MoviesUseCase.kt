package com.imrkjoseph.animenation.dashboard.shared.domain

import com.imrkjoseph.animenation.dashboard.shared.data.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MoviesUseCase @Inject constructor(
    private var moviesRepository: MoviesRepository
) {

    suspend fun searchMovies(movieName: String) = moviesRepository.searchMovies(movieName = movieName)

    suspend fun getMovieDetails(
        movieId: String,
        typeOfMovie: String
    ) = moviesRepository.getMovieDetails(
        movieId = movieId,
        typeOfMovie = typeOfMovie
    )

    suspend fun getStreamingLink(
        movieId: String,
        showId: String
    ) = moviesRepository.getStreamingLink(
        movieId = movieId,
        showId = showId
    )
}