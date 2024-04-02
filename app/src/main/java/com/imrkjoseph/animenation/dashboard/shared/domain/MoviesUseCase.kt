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
        data: VideoStreamRequest,
        isStreamFailed: Boolean = false
    ) = if (isStreamFailed) {
        moviesRepository.getAlternativeMovieStreamLink(
            movieId = data.detailsId ?: error("detailsId not found"),
            season = data.season,
            episode = data.episode,
        )
    } else {
        moviesRepository.getStreamingLink(
            movieId = data.episodeId ?: error("episodeId not found"),
            showId = data.showId ?: error("showId not found")
        )
    }

    data class VideoStreamRequest(
        val detailsId: String? = null,
        val episodeId: String? = null,
        val showId: String? = null,
        val season: Int? = null,
        val episode: Int? = null,
        val isStreamSeries: Boolean = false
    )
}