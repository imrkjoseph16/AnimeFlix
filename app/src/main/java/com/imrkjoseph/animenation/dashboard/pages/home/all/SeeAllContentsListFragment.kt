package com.imrkjoseph.animenation.dashboard.pages.home.all

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsLargeItem
import com.imrkjoseph.animenation.app.shared.binder.component.setupCardDetailsLargeItemBinder
import com.imrkjoseph.animenation.dashboard.pages.home.HomeSharedViewModel
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.AIRINGSCHEDULE
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.POPULARANIME
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.RECENTANIME
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.TOPANIME
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen.DetailsArguments
import com.imrkjoseph.animenation.databinding.FragmentSeeAllContentsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeeAllContentsListFragment : BaseFragment<FragmentSeeAllContentsListBinding>(bindingInflater = FragmentSeeAllContentsListBinding::inflate) {

    private val viewModel: SeeAllContentsListViewModel by viewModels()

    private val homeSharedViewModel: HomeSharedViewModel by viewModels()

    private val navArgs: SeeAllContentsListFragmentArgs by navArgs()

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupObserver()
        }
    }

    private fun FragmentSeeAllContentsListBinding.configureViews() {
        allAnimeList.setupAllAnimeList()

        setupPageTitle(animeType = navArgs.type)
        back.setOnClickListener { goBackToPreviousScreen() }
        onBackPressedCallBack(::goBackToPreviousScreen)
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(homeSharedViewModel) {
                launch {
                    getAnimeResults.collectLatest {
                        binding.allAnimeList.setItems(viewModel.getUiItems(animeUiItems = it))
                    }
                }

                // Check the navArgs type value,
                // on which the user's clicks came from AnimeList.
                verifyShouldGetDetails(animeType = navArgs.type)
            }
        }
    }

    private fun CustomRecyclerView.setupAllAnimeList() {
        addItemBindings(
            viewHolders = setupCardDetailsLargeItemBinder(
                dtoRetriever = CardDetailsLargeItem::dto,
                onItemClick =  {
                    goToDetailsScreen(
                        args = DetailsArguments(
                            detailsId = it.dto.itemId,
                            entryPointType = it.dto.itemEntryPoint
                        )
                    )
                }
            )
        )
    }

    private fun FragmentSeeAllContentsListBinding.setupPageTitle(animeType: AnimeType) {
       pageTitle = when(animeType) {
            TOPANIME -> R.string.section_top_10_anime_this_week
            RECENTANIME -> R.string.section_top_10_anime_this_week
            POPULARANIME -> R.string.section_popular_anime
            AIRINGSCHEDULE -> R.string.section_anime_airing_schedule
            else -> R.string.section_random_anime
        }
    }

    private fun goBackToPreviousScreen() = findNavController().popBackStack()

    private fun goToDetailsScreen(args: DetailsArguments) = findNavController().navigate(
        directions = SeeAllContentsListFragmentDirections.actionToDetailsScreen(
            argument = args
        )
    )

    override fun onDestroyView() {
        binding.allAnimeList.adapter = null
        super.onDestroyView()
    }
}