package com.imrkjoseph.animenation.dashboard.pages.explore.presentation

import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.component.CustomSearchView
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.binder.component.SectionTitleItem
import com.imrkjoseph.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.SpaceItemViewDtoBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupHorizontalListItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupSectionTitleItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupVerticalListItemBinder
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType.*
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.DetailsArguments
import com.imrkjoseph.animenation.databinding.FragmentExploreContentsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreContentsBinding>(bindingInflater = FragmentExploreContentsBinding::inflate) {

    private val viewModel: ExploreViewModel by viewModels()

    private var selectedExploreType: ExploreType = ANIME

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupExploreList()
            setupObserver()
        }
    }

    private fun FragmentExploreContentsBinding.configureViews() {
        searchContents.apply {
            binding?.searchView?.hint = getString(R.string.hint_search_contents)
            setViewListener(object : CustomSearchView.SearchViewListener {
                override fun onUserTyped(newText: String) = onExploreSearch(queryText = newText)
                override fun onSearchTap(searchKey: String) = onExploreSearch(queryText = searchKey)
                override fun onClearSearch() = onExploreSearch(queryText = "")
            })
        }

        exploreCategories.apply {
            setOnCheckedStateChangeListener { group, _ ->
                selectedExploreType = when(group.checkedChipId) {
                    animeCategory.id -> ANIME
                    koreanCategory.id -> KOREAN
                    else -> MOVIES
                }
                viewModel.getPreloadedContent(category = selectedExploreType)
            }
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

            viewModel.scrollToPosition.observe(viewLifecycleOwner) { position ->
                with(binding) {
                    if (position != null) {
                        try { searchScrollView.post(positionY = searchResultList.getChildAt(position).y.toInt()) }
                        catch (_: Exception) { }
                    }
                }
            }
        }
    }

    private fun NestedScrollView.post(positionY: Int) {
        post {
            fling(0)
            smoothScrollTo(0, positionY)
        }
    }

    private fun ExploreUiItems.updateUi() {
        with(binding) {
            uiItems = this@updateUi
            executePendingBindings()
            if (exploreLoading.not()) searchContents.onResultFinished.invoke()
        }
    }

    private fun FragmentExploreContentsBinding.setupExploreList() = searchResultList.setupPresetConfig()

    private fun CustomRecyclerView.setupPresetConfig() {
        addItemBindings(viewHolders = SpaceItemViewDtoBinder)
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = setupSectionTitleItemBinder(dtoRetriever = SectionTitleItem::dto))
        addItemBindings(viewHolders = setupHorizontalListItemBinder(
            onItemClick = { contents ->
                goToDetailsScreen(
                    details = DetailsArguments(
                        detailsId = contents.itemId,
                        entryPointType = contents.entryPointType,
                        typeOfMovie = contents.typeOfMovie
                    )
                )
            }
        ))
        addItemBindings(viewHolders = setupVerticalListItemBinder(
            onItemClick = { data ->
                goToDetailsScreen(
                    details = DetailsArguments(
                        detailsId = data.itemId,
                        entryPointType = data.entryPointType,
                        typeOfMovie = data.typeOfMovie
                    )
                )
            }
        ))
    }

    private fun onExploreSearch(queryText: String) = viewModel.exploreRandomContents(queryName = queryText)

    private fun goToDetailsScreen(
        details: DetailsArguments
    ) = findNavController().navigate(
        ExploreFragmentDirections.actionToDetailsScreen( argument = details)
    )
}