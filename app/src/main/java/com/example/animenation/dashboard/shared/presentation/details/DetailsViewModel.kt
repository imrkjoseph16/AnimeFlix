package com.example.animenation.dashboard.shared.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.app.util.coRunCatching
import com.example.animenation.dashboard.shared.domain.AnimeUseCase
import com.example.animenation.dashboard.pages.home.list.AnimeState
import com.example.animenation.dashboard.pages.home.list.GetAnimeDetails
import com.example.animenation.dashboard.pages.home.list.ShowDetailsError
import com.example.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.example.animenation.dashboard.shared.domain.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val animeUseCase: AnimeUseCase,
    private val moviesUseCase: MoviesUseCase,
    private val itemFactory: DetailsItemFactory
): ViewModel() {

    private var args = DetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private var currentEpisodeItems = 10

    private val _uiState = MutableStateFlow(AnimeState())
    val uiState = _uiState.asStateFlow()

    fun verifyDetailsState() {
        if (args.argument.entryPointType == EntryPointType.ANIME) {
            getAnimeDetails(detailsId = args.argument.detailsId)
        } else {
            getSeriesDetails(seriesId = args.argument.detailsId)
        }
    }

    private fun getAnimeDetails(detailsId: String) {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getAnimeDetails(animeId = detailsId)
            }.onSuccess { result ->
                getUiItems(result = result)
            }.onFailure {
                updateUiState(ShowDetailsError(throwable = it))
            }
        }
    }

    private fun getSeriesDetails(seriesId: String) {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.getSeriesDetails(seriesId = seriesId)
            }.onSuccess { result ->
                getUiItems(result = result)
            }.onFailure {
                updateUiState(ShowDetailsError(throwable = it))
            }
        }
    }

    private fun updateUiState(animeState: AnimeState) {
        _uiState.value = animeState
    }

    private fun getUiItems(
        result: DetailsFullData,
        episodesCount: Int = currentEpisodeItems,
        onBottomScrolled: Boolean = false
    ) {
        itemFactory.createOverview(
            details = result,
            episodesCount = episodesCount,
            onBottomScrolled = onBottomScrolled
        ).also { newUiItems ->
            _uiState.update {
                GetAnimeDetails(
                    detailsData = result,
                    otherEpisodeItems = newUiItems
                )
            }
        }
    }

    fun exploreAdditionalEpisodes() {
        viewModelScope.launch {
            currentEpisodeItems += 10
            _uiState.collectLatest { state ->
                if (state is GetAnimeDetails) {
                    state.detailsData?.let { data ->
                        getUiItems(
                            result = data,
                            episodesCount = currentEpisodeItems,
                            onBottomScrolled = true
                        )
                    }
                }
            }
        }
    }
}