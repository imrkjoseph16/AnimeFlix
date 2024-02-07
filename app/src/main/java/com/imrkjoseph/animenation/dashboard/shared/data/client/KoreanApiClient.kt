package com.imrkjoseph.animenation.dashboard.shared.data.client

import com.imrkjoseph.animenation.app.util.Default
import com.imrkjoseph.animenation.dashboard.shared.data.dto.korean.KoreanResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.korean.KoreanDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KoreanApiClient {

    @GET("info")
    suspend fun getSeriesDetails(
        @Query("id")
        seriesId: String
    ) : KoreanDetailsResponse

    @GET("{query}")
    suspend fun searchSeries(
        @Path("query")
        seriesName: String,
        @Query("page")
        page: Int = 1
    ) : KoreanResponse

    @GET("watch")
    suspend fun getSeriesStreamLink(
        @Query("episodeId")
        episodeId: String,
        @Query("server")
        serverName: String = Default.ASIAN_LOAD
    ) : StreamingLink
}