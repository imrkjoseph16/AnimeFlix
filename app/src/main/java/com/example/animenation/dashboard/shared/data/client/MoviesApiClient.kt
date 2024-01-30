package com.example.animenation.dashboard.shared.data.client

import com.example.animenation.app.util.Default
import com.example.animenation.dashboard.shared.data.dto.movies.ExploreResponse
import com.example.animenation.dashboard.shared.data.dto.movies.MoviesResponse
import com.example.animenation.dashboard.shared.data.dto.movies.SeriesDetailsResponse
import com.example.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiClient {

    @GET("info")
    suspend fun getSeriesDetails(
        @Query("id")
        seriesId: String
    ) : SeriesDetailsResponse

    @GET("{query}")
    suspend fun searchSeries(
        @Path("query")
        movieName: String,
        @Query("page")
        page: Int = 1
    ) : MoviesResponse

    @GET("{query}")
    suspend fun exploreSeries(
        @Path("query")
        seriesName: String,
        @Query("page")
        page: Int = 1
    ) : ExploreResponse

    @GET("watch")
    suspend fun getSeriesStreamLink(
        @Query("episodeId")
        episodeId: String,
        @Query("server")
        serverName: String = Default.ASIAN_LOAD
    ) : StreamingLink
}