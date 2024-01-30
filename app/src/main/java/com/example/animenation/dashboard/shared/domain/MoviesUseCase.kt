package com.example.animenation.dashboard.shared.domain

import com.example.animenation.dashboard.shared.data.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class MoviesUseCase @Inject constructor(
    private var moviesRepository: MoviesRepository
) {

    suspend fun getSeriesDetails(seriesId: String) = moviesRepository.getSeriesDetails(seriesId = seriesId)

    suspend fun searchSeries(movieName: String) = moviesRepository.searchSeries(movieName = movieName)

    suspend fun exploreSeries(seriesName: String) = moviesRepository.exploreSeries(seriesName = seriesName)

    suspend fun getStreamingLink(episodeId: String) = moviesRepository.getStreamingLink(episodeId = episodeId)

}