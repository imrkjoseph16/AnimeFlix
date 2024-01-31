package com.example.animenation.dashboard.pages.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animenation.app.util.Default.Companion.EXPLORE_DEFAULT_SEARCH
import com.example.animenation.app.util.coRunCatching
import com.example.animenation.dashboard.pages.explore.data.ExploreResultData
import com.example.animenation.dashboard.shared.data.extension.transformAnime
import com.example.animenation.dashboard.shared.data.extension.transformMovies
import com.example.animenation.dashboard.shared.data.ISearchAvailableAnime
import com.example.animenation.dashboard.shared.data.ISearchAvailableMovies
import com.example.animenation.dashboard.shared.data.extension.transformExplore
import com.example.animenation.dashboard.shared.domain.AnimeUseCase
import com.example.animenation.dashboard.shared.domain.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExploreUiItems(
    val animeResult: ExploreResultData? = null,
    val moviesResult: ExploreResultData? = null,
    val exploreResult: ExploreResultData? = null,
    val uiListItems: List<Any> = emptyList(),
    val exploreLoading: Boolean = false,
    val exploreError: Throwable? = null
)

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase,
    private val moviesUseCase: MoviesUseCase,
    private val findAnimeMoviesFactory: FindAnimeMoviesFactory
) : ViewModel() {

    private var exploreJob: Job? = null

    private val _uiState = MutableStateFlow(ExploreUiItems())
    val uiState = _uiState.asStateFlow()

    init {
        getPreloadedSeries()
    }

    private fun getPreloadedSeries() {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.exploreSeries(seriesName = EXPLORE_DEFAULT_SEARCH)
            }.onSuccess { result ->
                _uiState.update { it.copy(exploreResult = result.transformExplore()) }
                getUiItems(searchResult = _uiState.value)
            }.onFailure {
                handleFailure(error = it)
            }
        }
    }

    fun exploreAnimeMovies(searchName: String) {
        if (searchName.isEmpty()) resetState()
        else {
            exploreJob = viewModelScope.launch {
                updateLoading(loading = true)

                chainCall (
                    { animeUseCase.searchAnime(animeName = searchName) },
                    { moviesUseCase.searchSeries(movieName = searchName) },
                )

                updateLoading(loading = false)
            }
        }
    }

    private suspend fun<T : Any> chainCall(vararg calls: (suspend () -> T)) {
        run chain@ {
            calls.onEachIndexed { index, codeToExecute ->
                coRunCatching {
                    codeToExecute.invoke()
                }.onSuccess { result ->
                    handleChainCallback(result, index)
                }.onFailure {
                    handleFailure(error = it)
                    return@chain
                }
            }
        }
    }

    private fun updateLoading(
        loading: Boolean
    ) {
        _uiState.update { it.copy(exploreLoading = loading) }
    }

    private fun handleChainCallback(result: Any, index: Int) {
        when (result) {
            is ISearchAvailableAnime -> _uiState.update { it.copy(animeResult = result.transformAnime()) }
            is ISearchAvailableMovies -> _uiState.update { it.copy(moviesResult = result.transformMovies()) }
        }

        if (index == 1) getUiItems(searchResult = _uiState.value)
    }

    private fun handleFailure(error: Throwable) {
        _uiState.update { it.copy(exploreError = error) }
    }

    private fun getUiItems(searchResult: ExploreUiItems) {
        findAnimeMoviesFactory.createOverview(searchResult).also { newItems ->
            _uiState.update { it.copy(uiListItems = newItems) }
        }
    }

    private fun resetState() {
        exploreJob?.cancel()
        _uiState.update { it.copy(
            animeResult = null,
            moviesResult = null,
            exploreResult = it.exploreResult,
        ) }

        getUiItems(searchResult = _uiState.value)
    }
}