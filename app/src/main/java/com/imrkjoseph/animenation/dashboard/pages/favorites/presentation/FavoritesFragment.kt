package com.imrkjoseph.animenation.dashboard.pages.favorites.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.binder.component.FavoritesItem
import com.imrkjoseph.animenation.app.shared.binder.component.setupFavoritesItemBinder
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.app.util.setVisible
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen.DetailsArguments
import com.imrkjoseph.animenation.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(bindingInflater = FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()

    private var selectedCategory = EntryPointType.ALL.name

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupFavoritesList()
            setupObserver()
        }
    }

    private fun FragmentFavoritesBinding.configureViews() {
        favoritesCategories.apply {
            setOnCheckedStateChangeListener { group, _ ->
                selectedCategory = when(group.checkedChipId) {
                    animeCategory.id -> EntryPointType.ANIME.name
                    movieCategory.id -> EntryPointType.KOREAN.name
                    else -> EntryPointType.ALL.name
                }
                // Fetch the data from firebase.
                viewModel.getFavoritesList(favoritesType = selectedCategory)
            }
        }
    }

    private fun FragmentFavoritesBinding.setupFavoritesList() {
        with(favoritesList) {
            addItemBindings(viewHolders = setupFavoritesItemBinder(
                dtoRetriever = FavoritesItem::dto,
                onItemClick = {
                    goToDetailsScreen(
                        argsDetails = DetailsArguments(
                            detailsId = it.itemId,
                            entryPointType = EntryPointType.valueOf(it.type)
                        )
                    )
                }
            ))
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                launch {
                    uiState.collectLatest {
                        it.updateUi()
                    }
                }
            }

            getFavoritesList()
        }
    }

    private fun FavoritesUiModel.updateUi() {
        with(binding) {
            favoritesList.setItems(uiItems)
            emptyResult.setVisible(canShow = uiItems.isEmpty())
        }
    }

    private fun goToDetailsScreen(argsDetails: DetailsArguments) = findNavController().navigate(
        FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment( argument = argsDetails)
    )
}