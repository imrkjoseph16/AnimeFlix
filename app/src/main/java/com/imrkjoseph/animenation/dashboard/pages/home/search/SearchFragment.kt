package com.imrkjoseph.animenation.dashboard.pages.home.search

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.component.CustomSearchView
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.binder.component.setupSearchAnimeItemBinder
import com.imrkjoseph.animenation.app.shared.binder.data.SearchAnimeItem
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen.DetailsArguments
import com.imrkjoseph.animenation.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(bindingInflater = FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        onBackPressedCallBack(onBackClicked = findNavController()::popBackStack)
        with(binding) {
            configureViews()
            setupAnimeList()
            setupObserver()
        }
    }

    private fun FragmentSearchBinding.configureViews() {
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        searchAnime.apply {
            setViewListener(object : CustomSearchView.SearchViewListener {
                override fun onUserTyped(newText: String) = onAnimeSearch(queryText = newText)
                override fun onSearchTap(searchKey: String) = onAnimeSearch(queryText = searchKey)
                override fun onClearSearch() = onAnimeSearch(queryText = "")
            })
        }
    }

    private fun FragmentSearchBinding.setupAnimeList() = searchAnimeList.setupPresetConfig()

    private fun CustomRecyclerView.setupPresetConfig() {
        addItemBindings(viewHolders = setupSearchAnimeItemBinder(
            dtoRetriever = SearchAnimeItem::dto,
            onItemClick = { anime ->
                goToAnimeDetailsScreen(animeId = anime.itemId)
            }
        ))
    }

    private fun setupObserver() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                launch {
                    uiState.collectLatest { newItems ->
                        newItems.updateUi()
                    }
                }
            }
        }
    }

    private fun SearchUiItems.updateUi() {
        with(binding) {
            searchAnimeList.setItems(if(searchAnime.getText().isEmpty()) emptyList() else searchList)
            widgetError.root.isVisible = emptySearch
            searchAnime.onResultFinished.invoke()
        }
    }

    private fun onAnimeSearch(queryText: String?) = viewModel.searchAnime(animeName = queryText ?: "")

    private fun goToAnimeDetailsScreen(animeId: String) = findNavController().navigate(
        directions = SearchFragmentDirections.actionToDetailsScreen(
            argument = DetailsArguments(
                detailsId = animeId,
                entryPointType = EntryPointType.ANIME
            )
        )
    )
}