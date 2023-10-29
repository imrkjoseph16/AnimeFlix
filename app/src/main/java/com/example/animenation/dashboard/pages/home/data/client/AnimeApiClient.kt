package com.example.animenation.dashboard.pages.home.data.client

import com.example.animenation.dashboard.pages.home.data.dto.RecentEpisodeResponse
import com.example.animenation.dashboard.pages.home.data.dto.TopAnimeResponse
import retrofit2.http.GET

interface AnimeApiClient {

    @GET("top-airing")
    suspend fun getTopAnime(): TopAnimeResponse

    @GET("recent-episodes")
    suspend fun getRecentEpisode(): RecentEpisodeResponse
}