package com.imrkjoseph.animenation.dashboard.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.dashboard.shared.domain.AnimeUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.KoreanUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.SharedUseCase
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.domain.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val sharedUseCase: SharedUseCase,
    private val animeUseCase: AnimeUseCase,
    private val koreanUseCase: KoreanUseCase,
    private val moviesUseCase: MoviesUseCase
): ViewModel() {

    private val _sharedState = MutableStateFlow(SharedState())
    val sharedState = _sharedState.asStateFlow()

    fun getAnimeDetails(detailsId: String) {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getAnimeDetails(animeId = detailsId)
            }.onSuccess { result ->
                updateUiState(state = GetContentDetails(detailsData = result))
            }.onFailure {
                updateUiState(state = ShowDetailsError(throwable = it))
            }
        }
    }

    fun getKoreanDetails(seriesId: String) {
        viewModelScope.launch {
            coRunCatching {
                koreanUseCase.getSeriesDetails(seriesId = seriesId)
            }.onSuccess { result ->
                updateUiState(state = GetContentDetails(detailsData = result))
            }.onFailure {
                updateUiState(state = ShowDetailsError(throwable = it))
            }
        }
    }

    fun getMovieDetails(movieId: String, typeOfMovie: String) {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.getMovieDetails(movieId = movieId, typeOfMovie = typeOfMovie)
            }.onSuccess { result ->
                updateUiState(state = GetContentDetails(detailsData = result))
            }.onFailure {
                updateUiState(state = ShowDetailsError(throwable = it))
            }
        }
    }

    fun addToFavorites(details: FavoritesDetailsFullData) {
        viewModelScope.launch {
            coRunCatching {
                sharedUseCase.addToFavorites(data = details)
            }.onSuccess { isSuccess ->
                 updateUiState(state = ShowFavorites(isSuccess))
            }.onFailure {
                 updateUiState(ShowDetailsError(throwable = it))
            }
        }
    }

    private fun updateUiState(state: SharedState) {
        _sharedState.value = state
    }
}