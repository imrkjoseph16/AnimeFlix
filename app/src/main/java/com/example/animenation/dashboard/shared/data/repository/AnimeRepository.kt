package com.example.animenation.dashboard.shared.data.repository

import com.example.animenation.dashboard.shared.data.client.AnimeApiClient
import com.example.animenation.dashboard.shared.data.transformer.SharedTransformer
import dagger.Lazy
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    @Named("animeClient")
    private val retrofit: Lazy<Retrofit>,
    private val transformer: SharedTransformer
) {

    private val animeApi: AnimeApiClient by lazy { retrofit.get().create(AnimeApiClient::class.java) }

    suspend fun getTopAnimeList() = animeApi.getTopAnime()

    suspend fun getRecentEpisodeList() = animeApi.getRecentEpisode()

    suspend fun getAnimeDetails(animeId: String) = transformer.transformAnimeDetailsResponse(
        response = animeApi.getAnimeDetails(animeId = animeId)
    )

    suspend fun getStreamingLink(episodeId: String) = animeApi.getAnimeStreamLink(episodeId = episodeId)

    suspend fun searchAnime(animeName: String) = animeApi.searchAnime(animeName = animeName)
}