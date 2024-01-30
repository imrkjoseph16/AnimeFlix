package com.example.animenation.dashboard.shared.data.repository

import com.example.animenation.dashboard.shared.data.client.MoviesApiClient
import com.example.animenation.dashboard.shared.data.transformer.SharedTransformer
import dagger.Lazy
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    @Named("moviesClient")
    private val retrofit: Lazy<Retrofit>,
    private val transformer: SharedTransformer
) {

    private val moviesApi: MoviesApiClient by lazy { retrofit.get().create(MoviesApiClient::class.java) }

    suspend fun getSeriesDetails(seriesId: String) = transformer.transformSeriesDetailsResponse(
        response = moviesApi.getSeriesDetails(seriesId = seriesId)
    )

    suspend fun searchSeries(movieName: String) = moviesApi.searchSeries(movieName = movieName)

    suspend fun exploreSeries(seriesName: String) = moviesApi.exploreSeries(seriesName = seriesName)

    suspend fun getStreamingLink(episodeId: String) = moviesApi.getSeriesStreamLink(episodeId = episodeId)

}