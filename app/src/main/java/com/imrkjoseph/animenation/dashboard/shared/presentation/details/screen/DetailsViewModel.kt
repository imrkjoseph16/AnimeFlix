package com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
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
    private val args = DetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private var currentEpisodeItems = 10

    private val _uiState = MutableStateFlow(DetailsUiModel())
    val uiState = _uiState.asStateFlow()

    private var _canShowCasts = MutableLiveData(false)
    var canShowCasts: LiveData<Boolean> = _canShowCasts

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
        selectedType: DetailsOtherSelection,
        navArgs: DetailsArguments
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

    fun exploreAdditionalEpisodes(
        selectedType: DetailsOtherSelection,
        navArgs: DetailsArguments
    ) {
        viewModelScope.launch {
            currentEpisodeItems += 10
            _uiState.value.detailsData?.let {
                getUiSelectionItems(
                    result = it,
                    onBottomScrolled = true,
                    selectedType = selectedType,
                    navArgs = navArgs
                )
            }
        }
    }

    fun verifyCanShowCasts(data: DetailsFullData) = with(args.argument) {
        _canShowCasts.value = when {
            // Check the characters if it's not null,
            // this was another set of cast list since,
            // the Anime response is different data from Korean and Movies.
            data.characters.isNotEmpty() && entryPointType == EntryPointType.ANIME -> true
            // Check "casts" list if it's not null,
            // it means the entryType is either Korean or Movies,
            data.casts.isNotEmpty() -> true
            else -> false
        }
    }
}