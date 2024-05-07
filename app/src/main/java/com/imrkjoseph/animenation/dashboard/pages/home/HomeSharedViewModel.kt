package com.imrkjoseph.animenation.dashboard.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.Default.AnimeType
import com.imrkjoseph.animenation.app.util.Default.AnimeType.*
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.dashboard.pages.home.list.GetAnimeUiItems
import com.imrkjoseph.animenation.dashboard.shared.domain.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {

    val getAnimeResults = MutableStateFlow(value = GetAnimeUiItems())

    fun verifyShouldGetDetails(animeType: AnimeType) = when(animeType) {
        TOPANIME -> getTopAnimeList(includeCarouselList = false)
        RECENTANIME -> getRecentEpisodeList()
        POPULARANIME -> getPopularAnime()
        AIRINGSCHEDULE -> getAiringSchedule()
        else -> getRandomAnime()
    }

    fun getTopAnimeList(includeCarouselList: Boolean) {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getTopAnimeList()
            }.onSuccess { topAnimeResponse ->
                getAnimeResults.update {
                    it.copy(
                        carouselList = if (includeCarouselList) topAnimeResponse.results?.drop(5) else null,
                        topAnimeList = topAnimeResponse
                    )
                }
            }.onFailure {
                handleFailure(
                    topAnimeFailed = true
                )
            }
        }
    }

    fun getRecentEpisodeList() {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getRecentEpisodeList()
            }.onSuccess { recentEpisodeResponse ->
                getAnimeResults.update { it.copy(recentEpisodesList = recentEpisodeResponse) }
            }.onFailure {
                handleFailure(
                    recentEpisodeFailed = true
                )
            }
        }
    }

    fun getPopularAnime() {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getPopularAnime()
            }.onSuccess { popularAnimeResponse ->
                getAnimeResults.update { it.copy(popularAnimeList = popularAnimeResponse) }
            }.onFailure {
                handleFailure(
                    popularAnimeFailed = true
                )
            }
        }
    }

    fun getAiringSchedule() {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getAiringScheduleList()
            }.onSuccess { airingScheduleResponse ->
                getAnimeResults.update { it.copy(airingScheduleList = airingScheduleResponse) }
            }.onFailure {
                handleFailure(
                    airingScheduleFailed = true
                )
            }
        }
    }

    fun getRandomAnime() {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getRandomAnime()
            }.onSuccess { randomAnimeResponse ->
                getAnimeResults.update { it.copy(randomAnimeList = randomAnimeResponse) }
            }.onFailure {
                handleFailure(
                    randomAnimeFailed = true
                )
            }
        }
    }

    private fun handleFailure(
        topAnimeFailed: Boolean = false,
        recentEpisodeFailed: Boolean = false,
        popularAnimeFailed: Boolean = false,
        airingScheduleFailed: Boolean = false,
        randomAnimeFailed: Boolean = false,
    ) {
        getAnimeResults.apply {
            update {
                it.copy(
                    topAnimeFailed = topAnimeFailed,
                    recentEpisodeFailed = recentEpisodeFailed,
                    popularAnimeFailed = popularAnimeFailed,
                    airingScheduleFailed = airingScheduleFailed,
                    randomAnimeFailed = randomAnimeFailed,
                )
            }
        }
    }
}