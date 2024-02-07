package com.imrkjoseph.animenation.dashboard.pages.explore.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.app.shared.widget.SingleLiveEvent
import com.imrkjoseph.animenation.app.util.Default.Companion.ANIME_DEFAULT_SEARCH
import com.imrkjoseph.animenation.app.util.Default.Companion.KOREAN_DEFAULT_SEARCH
import com.imrkjoseph.animenation.app.util.Default.Companion.MOVIE_DEFAULT_SEARCH
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.dashboard.pages.explore.data.ExploreResultData
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.Companion.findItemViewPosition
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType.ANIME
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType.MOVIES
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType.KOREAN
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType.EXPLORE
import com.imrkjoseph.animenation.dashboard.shared.domain.AnimeUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.KoreanUseCase
import com.imrkjoseph.animenation.dashboard.shared.domain.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExploreUiItems(
    val animeResult: ExploreResultData? = null,
    val koreanResult: ExploreResultData? = null,
    val moviesResult: ExploreResultData? = null,
    val exploreResult: ExploreResultData? = null,
    val uiListItems: List<Any> = emptyList(),
    val exploreLoading: Boolean = true,
    val exploreError: Throwable? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase,
    private val koreanUseCase: KoreanUseCase,
    private val moviesUseCase: MoviesUseCase,
    private val exploreFactory: ExploreFactory
) : ViewModel() {

    private var searchAnimeJob: Job? = null

    private var searchKoreanJob: Job? = null

    private var searchMoviesJob: Job? = null

    private var explorePreloadedJob: Job? = null
    
    private var selectedCategoryType: ExploreType? = null

    private val _uiState = MutableStateFlow(value = ExploreUiItems())
    val uiState = _uiState.asStateFlow()

    private val _scrollToPosition = SingleLiveEvent<Int>()
    val scrollToPosition: LiveData<Int> = _scrollToPosition

    private val queryStream = MutableStateFlow("")

    init {
        getPreloadedContent()
        observeQueryStream()
    }

    fun getPreloadedContent(category: ExploreType = ANIME) {
        if (explorePreloadedJob?.isActive == true) explorePreloadedJob?.cancel()
        explorePreloadedJob = viewModelScope.launch {
            coRunCatching {
                when(category) {
                    ANIME -> animeUseCase.searchAnime(animeName = ANIME_DEFAULT_SEARCH)
                    KOREAN -> koreanUseCase.searchSeries(seriesName = KOREAN_DEFAULT_SEARCH)
                    else -> moviesUseCase.searchMovies(movieName = MOVIE_DEFAULT_SEARCH)
                }
            }.onSuccess { result ->
                selectedCategoryType = category
                updateLoading(loading = false)
                getResponseResult(exploreType = EXPLORE, response = result)
            }.onFailure {
                handleFailure(error = it)
            }
        }
    }

    private fun observeQueryStream() {
        viewModelScope.launch {
            queryStream.debounce(300L).collectLatest { queryName ->
                if (queryName.isEmpty()) resetState()
                else {
                    // Anime Results
                    searchAnimeResults(searchName = queryName)
                    // Korean Results
                    searchKoreanResults(searchName = queryName)
                    // Movies Results
                    searchMovieResults(searchName = queryName)
                }
            }
        }
    }

    fun exploreRandomContents(queryName: String) {
        queryStream.value = queryName
    }

    private fun searchAnimeResults(searchName: String) {
        searchAnimeJob = viewModelScope.launch {
            updateLoading(loading = true)

            coRunCatching {
                animeUseCase.searchAnime(animeName = searchName)
            }.onSuccess { response ->
                getResponseResult(exploreType = ANIME, response)
            }.onFailure {
                handleFailure(error = it)
            }

            updateLoading(loading = false)
        }
    }

    private fun searchKoreanResults(searchName: String) {
        searchKoreanJob = viewModelScope.launch {
            updateLoading(loading = true)

            coRunCatching {
                koreanUseCase.searchSeries(seriesName = searchName)
            }.onSuccess { response ->
                getResponseResult(exploreType = KOREAN, response)
            }.onFailure {
                handleFailure(error = it)
            }

            updateLoading(loading = false)
        }
    }

    private fun searchMovieResults(searchName: String) {
        searchMoviesJob = viewModelScope.launch {
            updateLoading(loading = true)

            coRunCatching {
                moviesUseCase.searchMovies(movieName = searchName)
            }.onSuccess { response ->
                getResponseResult(exploreType = MOVIES, response)
            }.onFailure {
                handleFailure(error = it)
            }

            updateLoading(loading = false)
        }
    }

    private fun getResponseResult(
        exploreType: ExploreType,
        response: ExploreResultData?
    ) {
        _uiState.update { items ->
            when(exploreType) {
                ANIME -> items.copy(animeResult = response)
                KOREAN -> items.copy(koreanResult = response)
                MOVIES -> items.copy(moviesResult = response)
                else -> items.copy(exploreResult = response)
            }
        }

        getUiItems(searchResult = _uiState.value)
        getScrollToBottomPosition(categoryType = exploreType)
    }

    private fun getUiItems(searchResult: ExploreUiItems) {
        exploreFactory.createOverview(
            selectedCategoryType = selectedCategoryType ?: ANIME,
            exploreItems = searchResult
        ).also { newItems ->
            _uiState.update { it.copy(uiListItems = newItems) }
        }
    }

    private fun getScrollToBottomPosition(categoryType: ExploreType = EXPLORE) {
        // We need to get the "item position" here,
        // so we can scroll to that section when the user's,
        // select a new category or search a new query.
        (if (categoryType == EXPLORE) getTitleItemPosition(type = EXPLORE)
        else getTitleItemPosition(type = ANIME))?.let { _scrollToPosition.value = it }
    }

    private fun resetState() {
        // Once the user clear's the search field,
        // cancel all the coroutine jobs.
        searchAnimeJob?.cancel()
        searchKoreanJob?.cancel()
        searchMoviesJob?.cancel()

        _uiState.update { it.copy(
            animeResult = null,
            koreanResult = null,
            moviesResult = null,
            exploreResult = it.exploreResult,
        ) }

        getUiItems(searchResult = _uiState.value)
        getScrollToBottomPosition()
    }

    private fun updateLoading(loading: Boolean) {
        _uiState.update { it.copy(exploreLoading = loading) }
    }

    private fun handleFailure(error: Throwable) {
        _uiState.update { it.copy(exploreError = error) }
    }

    private fun getTitleItemPosition(type: ExploreType) =
        findItemViewPosition(
            type = type,
            list = _uiState.value.uiListItems
        )
}