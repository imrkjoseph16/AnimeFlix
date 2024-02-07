package com.imrkjoseph.animenation.dashboard.shared.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Character
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiModel(
    val detailsData: DetailsFullData? = null,
    val castItems: List<Any> = emptyList(),
    val otherSelectionItems: List<Any> = emptyList(),
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemFactory: DetailsItemFactory
): ViewModel() {

    private val navArgs = DetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).argument

    private val _uiState = MutableStateFlow(DetailsUiModel())
    val uiState = _uiState.asStateFlow()

    private var currentEpisodeItems = 10

    fun getCastItems(
        cast: List<Character>? = null,
        actors: List<String>? = null
    ) = itemFactory.prepareCastList(
        cast = cast,
        actors = actors
    ).also { newCastItems ->
        _uiState.update {
            it.copy(
                castItems = newCastItems
            )
        }
    }

    fun getUiSelectionItems(
        result: DetailsFullData,
        onBottomScrolled: Boolean = false,
        selectedType: DetailsOtherSelection
    ) {
        // We should reset "currentEpisodeItems" when the user,
        // select in the selection categories so the,
        // initial displays of items is only 10 items again.
        if (onBottomScrolled.not()) currentEpisodeItems = 10

        itemFactory.prepareOtherSelectionList(
            details = result,
            episodesCount = currentEpisodeItems,
            onBottomScrolled = onBottomScrolled,
            selectedType = selectedType,
            entryPoint = navArgs.entryPointType
        ).also { newUiItems ->
            _uiState.update {
                it.copy(
                    detailsData = result,
                    otherSelectionItems = newUiItems
                )
            }
        }
    }

    fun exploreAdditionalEpisodes(selectedType: DetailsOtherSelection) {
        viewModelScope.launch {
            currentEpisodeItems += 10
            _uiState.value.detailsData?.let {
                getUiSelectionItems(
                    result = it,
                    onBottomScrolled = true,
                    selectedType = selectedType
                )
            }
        }
    }
}