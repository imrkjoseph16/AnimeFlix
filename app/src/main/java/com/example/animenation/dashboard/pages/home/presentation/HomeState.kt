package com.example.animenation.dashboard.pages.home.presentation

import com.example.animenation.dashboard.pages.home.data.dto.RecentEpisodeResponse
import com.example.animenation.dashboard.pages.home.data.dto.TopAnimeResponse

open class HomeState

object ShowHomeNoData : HomeState()

object ShowHomeLoading : HomeState()

object ShowHomeDismissLoading : HomeState()

data class ShowHomeError(val throwable: Throwable) : HomeState()

data class GetHomeUiItems(
    val topAnimeList: TopAnimeResponse? = null,
    val topAnimeFailed: Boolean = false,
    val recentEpisodesList: RecentEpisodeResponse? = null,
    val recentEpisodeFailed: Boolean = false
) : HomeState()