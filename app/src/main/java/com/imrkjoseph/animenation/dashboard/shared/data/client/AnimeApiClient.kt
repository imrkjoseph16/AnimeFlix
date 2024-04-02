package com.imrkjoseph.animenation.dashboard.shared.data.client

import com.imrkjoseph.animenation.app.util.Default.Companion.VIDS_STREAMING
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiClient {

    @GET("trending")
    suspend fun getTopAnime(): AnimeResponse

    @GET("recent-episodes")
    suspend fun getRecentEpisode(): AnimeResponse

    @GET("popular")
    suspend fun getPopularAnime(): AnimeResponse

    @GET("airing-schedule")
    suspend fun getAiringSchedule(): AnimeResponse

    @GET("new")
    suspend fun getRandomAnime(): AnimeResponse

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