package com.example.animenation.dashboard.pages.home.list

import com.example.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.example.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.example.animenation.dashboard.shared.data.dto.anime.RecentEpisodeResponse
import com.example.animenation.dashboard.shared.data.dto.anime.Result

open class AnimeState

object ShowAnimeNoData : AnimeState()

object ShowAnimeLoading : AnimeState()

object ShowAnimeDismissLoading : AnimeState()

data class GetAnimeDetails(
    val detailsData: DetailsFullData? = null,
    val otherEpisodeItems: List<Any>? = null,
) : AnimeState()

data class ShowDetailsError(val throwable: Throwable) : AnimeState()

data class GetAnimeUiItems(
    val carouselList: List<Result>? = null,
    val topAnimeList: AnimeResponse? = null,
    val topAnimeFailed: Boolean = false,
    val recentEpisodesList: RecentEpisodeResponse? = null,
    val recentEpisodeFailed: Boolean = false
) : AnimeState()