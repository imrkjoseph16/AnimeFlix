package com.example.animenation.dashboard.pages.home.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.animenation.app.util.coRunCatching
import com.example.animenation.dashboard.shared.domain.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val animeListItemFactory: AnimeListItemFactory,
    private val animeUseCase: AnimeUseCase
): ViewModel() {

    val getAnimeResults = MutableStateFlow(GetAnimeUiItems())

    val items: LiveData<List<Any>?> = getAnimeResults.map { newItems ->
        animeListItemFactory.createOverview(getHomeUiItems = newItems)
    }.asLiveData()

    init {
        getTopAnimeList()
        getRecentEpisodeList()
    }

    private fun getTopAnimeList() {
        viewModelScope.launch {
           coRunCatching {
               animeUseCase.getTopAnimeList()
           }.onSuccess { topAnimeResponse ->
               getAnimeResults.update {
                   it.copy(
                       carouselList = topAnimeResponse.results?.take(5),
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

    private fun getRecentEpisodeList() {
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

    private fun handleFailure(
        topAnimeFailed: Boolean = false,
        recentEpisodeFailed: Boolean = false
    ) {
        getAnimeResults.apply {
            update {
                it.copy(
                    topAnimeFailed = topAnimeFailed,
                    recentEpisodeFailed = recentEpisodeFailed
                )
            }
        }
    }
}