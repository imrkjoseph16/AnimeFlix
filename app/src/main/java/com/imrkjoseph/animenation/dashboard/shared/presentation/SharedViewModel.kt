package com.imrkjoseph.animenation.dashboard.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import com.imrkjoseph.animenation.app.util.Default.EntryPointType.*
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.domain.AnimeUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.KoreanUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.MoviesUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.SharedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val sharedUseCase: SharedUseCase,
    private val animeUseCase: AnimeUseCase,
    private val koreanUseCase: KoreanUseCase,
    private val moviesUseCase: MoviesUseCase
): ViewModel() {

    private var _sharedState = MutableSharedFlow<SharedState>()
    var sharedState = _sharedState.asSharedFlow()

    fun verifyDetailsState(
        entryPoint: EntryPointType,
        detailsId: String,
        typeOfMovie: String?
    ) {
        when(entryPoint) {
            ANIME -> getAnimeDetails(detailsId = detailsId)
            KOREAN -> getKoreanDetails(seriesId = detailsId)
            else -> getMovieDetails(
                movieId = detailsId,
                typeOfMovie = typeOfMovie.orEmpty()
            )
        }
    }

    private fun getAnimeDetails(detailsId: String) {
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

    private fun getKoreanDetails(seriesId: String) {
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

    private fun getMovieDetails(movieId: String, typeOfMovie: String) {
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
        viewModelScope.launch {
            _sharedState.emit(value = state)
        }
    }
}