package com.imrkjoseph.animenation.dashboard.shared.domain

import com.imrkjoseph.animenation.dashboard.shared.data.repository.KoreanRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class KoreanUseCase @Inject constructor(
    private var koreanRepository: KoreanRepository
) {

    suspend fun getSeriesDetails(seriesId: String) = koreanRepository.getSeriesDetails(seriesId = seriesId)

    suspend fun searchSeries(seriesName: String) = koreanRepository.searchSeries(seriesName = seriesName)

    suspend fun getStreamingLink(episodeId: String) = koreanRepository.getStreamingLink(episodeId = episodeId)
}