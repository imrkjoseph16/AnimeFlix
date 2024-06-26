package com.imrkjoseph.animenation.dashboard.shared.data.repository

import com.imrkjoseph.animenation.dashboard.shared.data.client.MoviesApiClient
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformAlternativeMovieStream
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformMovieDetails
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformMovies
import dagger.Lazy
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    @Named("moviesClient")
    private val movies: Lazy<Retrofit>,
    @Named("alternativeStreamClient")
    private val streamLinkAlternative: Lazy<Retrofit>
) {

    private val moviesApi: MoviesApiClient by lazy { movies.get().create(MoviesApiClient::class.java) }

    private val alternativeStreamApi: MoviesApiClient by lazy { streamLinkAlternative.get().create(MoviesApiClient::class.java) }

    suspend fun searchMovies(movieName: String) = moviesApi.searchMovies(movieName = movieName).transformMovies()

    suspend fun getMovieDetails(
        movieId: String,
        typeOfMovie: String
    ) = moviesApi.getMovieDetails(
            movieId = movieId,
            typeOfMovie = typeOfMovie
        ).transformMovieDetails()

    suspend fun getStreamingLink(
        movieId: String,
        showId: String? = null
    ) = moviesApi.getMovieStreamLink(
        movieId = movieId,
        showId = showId
    )

    suspend fun getAlternativeMovieStreamLink(
        movieId: String,
        season: Int? = null,
        episode: Int? = null,
    ) = alternativeStreamApi.getAlternativeMovieStreamLink(
        movieId = movieId,
        season = season,
        episode = episode
    ).transformAlternativeMovieStream()
}