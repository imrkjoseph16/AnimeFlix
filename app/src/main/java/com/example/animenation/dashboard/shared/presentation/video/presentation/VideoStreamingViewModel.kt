package com.example.animenation.dashboard.shared.presentation.video.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.app.util.coRunCatching
import com.example.animenation.dashboard.shared.domain.AnimeUseCase
import com.example.animenation.dashboard.shared.domain.MoviesUseCase
import com.example.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoStreamingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val animeUseCase: AnimeUseCase,
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private var args = VideoStreamingArgs.fromSavedStateHandle(savedStateHandle)

    private val _streamLink = MutableLiveData<StreamingLink>()
    val streamLink: LiveData<StreamingLink> = _streamLink

    fun getStreamingState() {
        if (args.argument.entryPointType == EntryPointType.ANIME) {
            getAnimeStreamLink(episodeId = args.argument.episodeId)
        } else {
            getSeriesStreamLink(episodeId = args.argument.episodeId)
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

    private fun getSeriesStreamLink(episodeId: String) {
        viewModelScope.launch {
            coRunCatching {
                moviesUseCase.getStreamingLink(episodeId = episodeId)
            }.onSuccess { result ->
                _streamLink.value = result
            }
        }
    }
}