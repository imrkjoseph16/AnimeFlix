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
import com.imrkjoseph.animenation.dashboard.shared.domain.MoviesUseCase.VideoStreamRequest
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
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

    var currentEpisode = args.selectedEpisode

    fun getStreamingState(
        data: VideoStreamRequest
    ) {
        when(args.entryPointType) {
            EntryPointType.ANIME -> getAnimeStreamLink(episodeId = data.episodeId)
            EntryPointType.KOREAN -> getKoreanStreamLink(episodeId = data.episodeId)
            else -> getMovieStreamLink(data = data)
        }
    }

    private fun getAnimeStreamLink(episodeId: String?) {
        viewModelScope.launch {
            coRunCatching {
                animeUseCase.getStreamingLink(episodeId = episodeId ?: error("episodeId not found"))
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }

    private fun getKoreanStreamLink(episodeId: String?) {
        viewModelScope.launch {
            coRunCatching {
                koreanUseCase.getStreamingLink(episodeId = episodeId ?: error("episodeId not found"))
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }

    private fun getMovieStreamLink(
        data: VideoStreamRequest
    ) {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.getStreamingLink(
                    VideoStreamRequest(
                        episodeId = data.episodeId,
                        showId = data.showId
                    )
                )
            }.onSuccess { result ->
                _streamLink.value = result
            }.onFailure {
                // If the streaming link from TMDB was failed,
                // we can check if the alternative streaming link,
                // have available media resources.
                getAlternativeStreamLink(
                    detailsId = data.detailsId,
                    isSeries = data.isStreamSeries,
                    currentEpisode = currentEpisode
                )
            }
        }
    }

    private fun getAlternativeStreamLink(
        detailsId: String?,
        isSeries: Boolean,
        currentEpisode: Int
    ) {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.getStreamingLink(
                    isStreamFailed = true,
                    data = VideoStreamRequest(
                        detailsId = detailsId,
                        season = if (isSeries) 1 else null,
                        episode = if (isSeries) currentEpisode else null,
                    )
                )
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }

    fun getUiEpisodeItems(
        details: DetailsFullData?
    ) {
        itemFactory.prepareList(
            entryPoint = args.entryPointType,
            result = details,
            currentEpisode = currentEpisode
        ).also { episodeItems ->
            _uiItems.update { it.copy(episodeItems = episodeItems) }
        }
    }
}