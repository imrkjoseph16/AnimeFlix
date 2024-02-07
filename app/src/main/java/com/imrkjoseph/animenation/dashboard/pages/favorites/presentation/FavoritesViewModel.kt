package com.imrkjoseph.animenation.dashboard.pages.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.domain.SharedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesUiModel(
    var uiItems: List<Any> = emptyList(),
    val loading: Boolean = false
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val sharedUseCase: SharedUseCase,
    private val favoritesFactory: FavoritesFactory
) : ViewModel() {

    var uiState = MutableStateFlow(value = FavoritesUiModel())

    fun getFavoritesList(favoritesType: String = EntryPointType.ALL.name) {
        viewModelScope.launch {
            sharedUseCase.getFavorites(
                favoritesType = favoritesType
            ).collectLatest(::getUiItems)
        }
    }

    private fun getUiItems(data: List<FavoritesDetailsFullData>?) {
        favoritesFactory.createOverview(data = data).also { uiItems ->
            uiState.update { it.copy(uiItems = uiItems) }
        }
    }
}