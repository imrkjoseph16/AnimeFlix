package com.example.animenation.dashboard.shared.data.client

import com.example.animenation.app.util.Default.Companion.VIDS_STREAMING
import com.example.animenation.dashboard.shared.data.dto.anime.RecentEpisodeResponse
import com.example.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.example.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import com.example.animenation.dashboard.shared.data.dto.anime.AnimeDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiClient {

    @GET("top-airing")
    suspend fun getTopAnime(): AnimeResponse

    @GET("recent-episodes")
    suspend fun getRecentEpisode(): RecentEpisodeResponse

    @GET("info/{id}")
    suspend fun getAnimeDetails(
        @Path("id")
        animeId: String
    ) : AnimeDetailsResponse

    @GET("watch/{episodeId}")
    suspend fun getAnimeStreamLink(
        @Path("episodeId")
        episodeId: String,
        @Query("serverName")
        serverName: String = VIDS_STREAMING
    ) : StreamingLink

    @GET("{query}")
    suspend fun searchAnime(
        @Path("query")
        animeName: String,
        @Query("page")
        page: Int = 1
    ) : AnimeResponse
}