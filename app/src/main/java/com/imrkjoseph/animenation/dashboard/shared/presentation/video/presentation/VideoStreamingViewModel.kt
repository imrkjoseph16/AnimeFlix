package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.dashboard.shared.domain.AnimeUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.KoreanUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.MoviesUseCase
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VideoStreamingUiItems(
    val episodeItems: List<Any>? = null
)

@HiltViewModel
class VideoStreamingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val animeUseCase: AnimeUseCase,
    private val koreanUseCase: KoreanUseCase,
    private val moviesUseCase: MoviesUseCase,
    private val itemFactory: VideoStreamingFactory
) : ViewModel() {

    private var args = VideoStreamingArgs.fromSavedStateHandle(savedStateHandle).argument

    private val _streamLink = MutableLiveData<StreamingLink>()
    val streamLink: LiveData<StreamingLink> = _streamLink

    private val _uiItems = MutableStateFlow(value = VideoStreamingUiItems())
    val uiItems = _uiItems.asStateFlow()

    fun getStreamingState() {
        when(args.entryPointType) {
            EntryPointType.ANIME -> getAnimeStreamLink(episodeId = args.episodeId)
            EntryPointType.KOREAN -> getKoreanStreamLink(episodeId = args.episodeId)
            else -> getMovieStreamLink(episodeId = args.episodeId, showId = args.showId)
        }

        itemFactory.prepareList(
            entryPoint = args.entryPointType,
            result = args.details
        ).also { episodeItems ->
            _uiItems.update { it.copy(episodeItems = episodeItems) }
        }
    }

    private fun getAnimeStreamLink(episodeId: String) {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getStreamingLink(episodeId = episodeId)
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }

    private fun getKoreanStreamLink(episodeId: String) {
        viewModelScope.launch {
            coRunCatching {
                koreanUseCase.getStreamingLink(episodeId = episodeId)
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }

    private fun getMovieStreamLink(episodeId: String, showId: String?) {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.getStreamingLink(
                    movieId = episodeId,
                    showId = showId ?: error("showId not found")
                )
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }
}