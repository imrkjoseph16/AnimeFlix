package com.example.animenation.dashboard.shared.domain

import com.example.animenation.dashboard.shared.data.repository.AnimeRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class AnimeUseCase @Inject constructor(
    private var animeRepository: AnimeRepository
) {

    suspend fun getTopAnimeList() = animeRepository.getTopAnimeList()

    suspend fun getRecentEpisodeList() = animeRepository.getRecentEpisodeList()

    suspend fun getAnimeDetails(animeId: String) = animeRepository.getAnimeDetails(animeId = animeId)
    
    suspend fun getStreamingLink(episodeId: String) = animeRepository.getStreamingLink(episodeId = episodeId)

    suspend fun searchAnime(animeName: String) = animeRepository.searchAnime(animeName = animeName)
}