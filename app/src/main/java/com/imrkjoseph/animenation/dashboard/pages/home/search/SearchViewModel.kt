package com.imrkjoseph.animenation.dashboard.pages.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.dashboard.pages.explore.data.ExploreResultData
import com.imrkjoseph.animenation.dashboard.shared.domain.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiItems(
    val searchList: List<Any>? = emptyList(),
    val emptySearch: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase,
    private val searchFactory: SearchFactory
) : ViewModel() {

    private var searchAnimeJob: Job? = null

    private val _uiState = MutableStateFlow(SearchUiItems())
    val uiState = _uiState.asStateFlow()

    fun searchAnime(animeName: String) {
        if (animeName.isEmpty()) resetState()
        else {
            searchAnimeJob = viewModelScope.launch {
                coRunCatching {
                    animeUseCase.searchAnime(animeName = animeName)
                }.onSuccess { searchResult ->
                    getUiItems(data = searchResult)
                }.onFailure {
                    _uiState.update { it.copy(emptySearch = true) }
                }
            }
        }
    }

    private fun getUiItems(data: ExploreResultData?) {
        data?.let {
            searchFactory.createOverview(it).also { newItems ->
                _uiState.update { items -> items.copy(
                    searchList = newItems,
                    emptySearch = newItems?.isEmpty() == true.or(items.emptySearch))
                }
            }
        } ?: _uiState.update { it.copy(emptySearch = true) }
    }

    private fun resetState() {
        searchAnimeJob?.cancel()

        _uiState.update { it.copy(
            searchList = null,
            emptySearch = true,
        ) }
    }
}