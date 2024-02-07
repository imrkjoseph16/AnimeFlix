package com.imrkjoseph.animenation.dashboard.shared.data.repository

import com.imrkjoseph.animenation.dashboard.shared.data.client.AnimeApiClient
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformAnime
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformAnimeDetails
import dagger.Lazy
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    @Named("animeClient")
    private val retrofit: Lazy<Retrofit>
) {

    private val animeApi: AnimeApiClient by lazy { retrofit.get().create(AnimeApiClient::class.java) }

    suspend fun getTopAnimeList() = animeApi.getTopAnime()

    suspend fun getRecentEpisodeList() = animeApi.getRecentEpisode()

    suspend fun getAiringSchedule() = animeApi.getAiringSchedule()

    suspend fun getRandomAnime() = animeApi.getRandomAnime()

    suspend fun getPopularAnime() = animeApi.getPopularAnime()

    suspend fun getAnimeDetails(animeId: String) = animeApi.getAnimeDetails(animeId = animeId).transformAnimeDetails()

    suspend fun getStreamingLink(episodeId: String) = animeApi.getAnimeStreamLink(episodeId = episodeId)

    suspend fun searchAnime(animeName: String) = animeApi.searchAnime(animeName = animeName).transformAnime()
}