package com.imrkjoseph.animenation.dashboard.shared.data.client

import com.imrkjoseph.animenation.dashboard.shared.data.dto.movies.MovieDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.movies.MoviesResponse
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.FileMoonStreamLink
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiClient {
    @GET("{query}")
    suspend fun searchMovies(
        @Path("query")
        movieName: String,
        @Query("page")
        page: Int = 1
    ) : MoviesResponse

    @GET("info/{id}")
    suspend fun getMovieDetails(
        @Path("id")
        movieId: String,
        @Query("type")
        typeOfMovie: String
    ) : MovieDetailsResponse

    @GET("watch/{movieId}")
    suspend fun getMovieStreamLink(
        @Path("movieId")
        movieId: String,
        @Query("id")
        showId: String? = null
    ) : StreamingLink

    @GET("vidsrc/{movieId}")
    suspend fun getAlternativeMovieStreamLink(
        @Path("movieId")
        movieId: String,
        @Query("s")
        season: Int? = null,
        @Query("e")
        episode: Int? = null
    ) : FileMoonStreamLink
}