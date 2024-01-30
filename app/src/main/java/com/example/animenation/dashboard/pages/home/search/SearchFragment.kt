package com.example.animenation.dashboard.pages.home.search

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemDiffCallback
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.app.shared.binder.component.getSearchAnimeItemBinder
import com.example.animenation.app.shared.binder.data.SearchAnimeItem
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.dashboard.shared.presentation.details.DetailsArguments
import com.example.animenation.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(bindingInflater = FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
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

        searchAnime.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onAnimeSearch(queryText = query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onAnimeSearch(queryText = newText)
                return false
            }
        })

        onBackPressedCallBack(onBackClicked = findNavController()::popBackStack)
    }

    private fun FragmentSearchBinding.setupAnimeList() = searchAnimeList.setupPresetConfig()

    private fun CustomRecyclerView.setupPresetConfig() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemDiffCallback())
        addItemBindings(viewHolders = getSearchAnimeItemBinder(
            dtoRetriever = SearchAnimeItem::dto,
            onItemClick = { anime ->
                goToAnimeDetailsScreen(animeId = anime.itemId)
            }
        ))
    }

    private fun setupObserver() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    launch {
                        uiState.collectLatest { newItems ->
                            newItems.updateUi()
                        }
                    }
                }
            }
        }
    }

    private fun SearchUiItems.updateUi() {
        with(binding) {
            searchAnimeList.setItems(if(searchAnime.query.isEmpty()) emptyList() else searchList)
            widgetError.root.isVisible = emptySearch
        }
    }

    private fun onAnimeSearch(queryText: String?) = viewModel.searchAnime(animeName = queryText ?: "")

    private fun goToAnimeDetailsScreen(animeId: String) = findNavController().navigate(
        directions = SearchFragmentDirections.actionToAnimeDetailsScreen(
            argument = DetailsArguments(
                detailsId = animeId,
                entryPointType = EntryPointType.ANIME
            )
        )
    )
}