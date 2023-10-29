package com.example.animenation.dashboard.pages.home.domain

import com.example.animenation.dashboard.pages.home.data.client.AnimeApiClient
import dagger.Lazy
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Inject

@Reusable
class HomeUseCase @Inject constructor(
    private val retrofit: Lazy<Retrofit>
) {

    private val registerApi: AnimeApiClient by lazy { retrofit.get().create(AnimeApiClient::class.java) }

    suspend fun getTopAnimeList() = registerApi.getTopAnime()

    suspend fun getRecentEpisodeList() = registerApi.getRecentEpisode()
}