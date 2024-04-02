package com.imrkjoseph.animenation.dashboard.shared.data.repository

import com.imrkjoseph.animenation.dashboard.shared.data.client.KoreanApiClient
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformKorean
import com.imrkjoseph.animenation.dashboard.shared.data.extension.transformSeriesDetails
import dagger.Lazy
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class KoreanRepository @Inject constructor(
    @Named("seriesClient")
    private val retrofit: Lazy<Retrofit>
) {

    private val moviesApi: KoreanApiClient by lazy { retrofit.get().create(KoreanApiClient::class.java) }

    suspend fun getSeriesDetails(seriesId: String) = moviesApi.getSeriesDetails(seriesId = seriesId).transformSeriesDetails()

    suspend fun searchSeries(seriesName: String) = moviesApi.searchSeries(seriesName = seriesName).transformKorean()

    suspend fun getStreamingLink(episodeId: String) = moviesApi.getSeriesStreamLink(episodeId = episodeId)
}