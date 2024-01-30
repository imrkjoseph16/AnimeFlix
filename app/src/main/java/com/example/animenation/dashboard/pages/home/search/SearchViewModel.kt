package com.example.animenation.dashboard.pages.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animenation.app.util.coRunCatching
import com.example.animenation.dashboard.shared.data.extension.transformAnime
import com.example.animenation.dashboard.shared.data.ISearchAvailableAnime
import com.example.animenation.dashboard.shared.domain.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _uiState = MutableStateFlow(SearchUiItems())
    val uiState = _uiState.asStateFlow()

    fun searchAnime(animeName: String) {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.searchAnime(animeName = animeName)
            }.onSuccess { searchResult ->
                getUiItems(searchResult = searchResult)
            }.onFailure {
                _uiState.update { it.copy(emptySearch = true) }
            }
        }
    }

    private fun getUiItems(searchResult: ISearchAvailableAnime) {
        searchResult.transformAnime()?.let { data ->
            searchFactory.createOverview(data).also { newItems ->
                _uiState.update { it.copy(
                    searchList = newItems,
                    emptySearch = newItems?.isEmpty() == true.or(it.emptySearch))
                }
            }
        }
    }
}