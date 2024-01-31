package com.example.animenation.dashboard.pages.explore.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.CustomSearchView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.app.shared.binder.component.SectionTitleItemBinder
import com.example.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.example.animenation.app.shared.binder.component.SpaceItemViewDtoBinder
import com.example.animenation.app.shared.binder.component.getWidgetExploreItemBinder
import com.example.animenation.app.shared.binder.component.getWidgetItemBinder
import com.example.animenation.app.shared.binder.data.AnimeItem
import com.example.animenation.app.shared.binder.data.ExploreSeriesItem
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.dashboard.shared.presentation.details.DetailsArguments
import com.example.animenation.databinding.FragmentFindAnimeMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindAnimeMoviesFragment : BaseFragment<FragmentFindAnimeMoviesBinding>(bindingInflater = FragmentFindAnimeMoviesBinding::inflate) {

    private val viewModel: ExploreViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupExploreList()
            setupObserver()
        }
    }

    private fun FragmentFindAnimeMoviesBinding.configureViews() {
        searchAnimeMovies.apply {
            setViewListener(object : CustomSearchView.SearchViewListener {
                override fun onUserTyped(newText: String) = onExploreSearch(queryText = newText)
                override fun onSearchTap(searchKey: String) = onExploreSearch(queryText = searchKey)
                override fun onClearSearch() = onExploreSearch(queryText = "")
            })
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        uiState.collectLatest { newState ->
                            newState.updateUi()
                        }
                    }
                }
            }
        }
    }

    private fun ExploreUiItems.updateUi() {
        with(binding) {
            uiItems = this@updateUi
            executePendingBindings()
            if (uiListItems.isNotEmpty()) searchResultList.smoothScrollToPosition(0)
            if (exploreLoading.not()) searchAnimeMovies.onResultFinished.invoke()
        }
    }

    private fun FragmentFindAnimeMoviesBinding.setupExploreList() = searchResultList.setupPresetConfig()

    private fun CustomRecyclerView.setupPresetConfig() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        addItemBindings(viewHolders = SpaceItemViewDtoBinder)
        addItemBindings(viewHolders = SectionTitleItemBinder)
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = getWidgetExploreItemBinder(
            dtoRetriever = ExploreSeriesItem::payload,
            onItemClick = { series ->
                goToAnimeDetailsScreen(animeId = series.itemId)
            }
        ))
        addItemBindings(viewHolders = getWidgetItemBinder(
            dtoRetriever = AnimeItem::payload,
            onItemClick = { anime ->
                goToAnimeDetailsScreen(animeId = anime.itemId, type = anime.type)
            }
        ))
    }

    private fun onExploreSearch(queryText: String) = viewModel.exploreAnimeMovies(searchName = queryText)

    private fun goToAnimeDetailsScreen(
        animeId: String,
        type: EntryPointType = EntryPointType.ANIME
    ) = findNavController().navigate(
        directions = FindAnimeMoviesFragmentDirections.actionToAnimeDetailsScreen(
            argument = DetailsArguments(
                detailsId = animeId,
                entryPointType = type
            )
        )
    )
}