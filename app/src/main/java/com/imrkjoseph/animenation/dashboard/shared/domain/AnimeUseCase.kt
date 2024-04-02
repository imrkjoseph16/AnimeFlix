package com.imrkjoseph.animenation.dashboard.shared.domain

import com.imrkjoseph.animenation.dashboard.shared.data.repository.AnimeRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class AnimeUseCase @Inject constructor(
    private var animeRepository: AnimeRepository
) {

    suspend fun getTopAnimeList() = animeRepository.getTopAnimeList()

    suspend fun getRecentEpisodeList() = animeRepository.getRecentEpisodeList()

    suspend fun getPopularAnime() = animeRepository.getPopularAnime()

    suspend fun getAiringScheduleList() = animeRepository.getAiringSchedule()

    suspend fun getRandomAnime() = animeRepository.getRandomAnime()

    suspend fun getAnimeDetails(animeId: String) = animeRepository.getAnimeDetails(animeId = animeId)
    
    suspend fun getStreamingLink(episodeId: String) = animeRepository.getStreamingLink(episodeId = episodeId)

    suspend fun searchAnime(animeName: String) = animeRepository.searchAnime(animeName = animeName)
}