package com.imrkjoseph.animenation.dashboard.pages.home.list

import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.Result

open class AnimeState

data class GetAnimeUiItems(
    val carouselList: List<Result>? = null,
    // Top/Trending Anime
    val topAnimeList: AnimeResponse? = null,
    val topAnimeFailed: Boolean = false,
    // Latest Episode Release
    val recentEpisodesList: AnimeResponse? = null,
    val recentEpisodeFailed: Boolean = false,
    // Popular Anime's}
    val popularAnimeList: AnimeResponse? = null,
    val popularAnimeFailed: Boolean = false,
    // Anime Airing Schedule
    val airingScheduleList: AnimeResponse? = null,
    val airingScheduleFailed: Boolean = false,
    // Anime Airing Schedule
    val randomAnimeList: AnimeResponse? = null,
    val randomAnimeFailed: Boolean = false
) : AnimeState()