package com.example.animenation.dashboard.pages.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.animenation.app.util.coRunCatching
import com.example.animenation.dashboard.pages.home.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeItemFactory: HomeItemFactory,
    private val homeUseCase: HomeUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeState>(ShowHomeNoData)
    val uiState = _uiState.asStateFlow()

    private val getAnimeResults = MutableStateFlow(GetHomeUiItems())

    val items: LiveData<List<Any>?> = getAnimeResults.map { newItems ->
        homeItemFactory.createOverview(getHomeUiItems = newItems)
    }.asLiveData()

    init {
        getTopAnimeList()
        getRecentEpisodeList()
    }

    private fun getTopAnimeList() {
        viewModelScope.launch {
           coRunCatching {
               homeUseCase.getTopAnimeList()
           }.onSuccess { topAnimeResponse ->
               getAnimeResults.update { it.copy(topAnimeList = topAnimeResponse) }
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
                homeUseCase.getRecentEpisodeList()
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
            map {
                update {
                    it.copy(
                        topAnimeFailed = topAnimeFailed,
                        recentEpisodeFailed = recentEpisodeFailed
                    )
                }
            }
        }
    }
}