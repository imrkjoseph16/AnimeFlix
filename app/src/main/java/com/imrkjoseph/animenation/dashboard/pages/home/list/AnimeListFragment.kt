package com.imrkjoseph.animenation.dashboard.pages.home.list

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.binder.component.SectionTitleItem
import com.imrkjoseph.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.SpaceItemViewDtoBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupHorizontalListItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupSectionTitleItemBinder
import com.imrkjoseph.animenation.app.util.Default.AnimeType
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import com.imrkjoseph.animenation.dashboard.pages.home.HomeSharedViewModel
import com.imrkjoseph.animenation.dashboard.pages.home.list.TopAnimeAdapter.OnCarouselItemClick
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.Result
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen.DetailsArguments
import com.imrkjoseph.animenation.databinding.FragmentListAnimeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : BaseFragment<FragmentListAnimeBinding>(bindingInflater = FragmentListAnimeBinding::inflate) {

    private val viewModel: AnimeListViewModel by viewModels()

    private val homeSharedViewModel: HomeSharedViewModel by activityViewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupAnimeList()
            setupObserver()
        }
    }

    private fun FragmentListAnimeBinding.configureViews() {
        searchAnime.setOnClickListener {
            goToSearchAnimeScreen()
        }
    }

    private fun FragmentListAnimeBinding.setupAnimeList() {
        homeScreenItems.setupPresetConfig()
        executePendingBindings()
    }

    private fun CustomRecyclerView.setupPresetConfig() {
        addItemBindings(viewHolders = SpaceItemViewDtoBinder)
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = setupSectionTitleItemBinder(
            dtoRetriever = SectionTitleItem::dto,
            onSeeAllClick = {
                goToSeeAllContentsScreen(type = it.id as AnimeType)
            }
        ))
        addItemBindings(viewHolders = setupHorizontalListItemBinder(
            onItemClick = { contents ->
                goToAnimeDetailsScreen(animeId = contents.itemId)
            },
            onLargeCardClick = { details ->
                goToAnimeDetailsScreen(animeId = details.dto.itemId)
            }
        ))
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(homeSharedViewModel) {
                launch {
                    getAnimeResults.collectLatest {
                        binding.apply {
                            homeScreenItems.setItems(viewModel.getUiItems(animeUiItems = it))
                            setupTopAnimeCarousel(it.carouselList)
                        }
                    }
                }

                getTopAnimeList(includeCarouselList = true)
                getRecentEpisodeList()
                getRandomAnime()
                getPopularAnime()
                getAiringSchedule()
            }
        }
    }

    private fun FragmentListAnimeBinding.setupTopAnimeCarousel(results: List<Result>?) {
        results?.let { anime ->
            topAnimeCarousel.apply {
                val topAnimeAdapter = TopAnimeAdapter(topAnimeList = anime)
                adapter = topAnimeAdapter
                carouselIndicator.attachTo(viewPager2 = this)

                topAnimeAdapter.setOnItemClickListener(object : OnCarouselItemClick {
                    override fun onCarouselClick(animeId: String) =
                        goToAnimeDetailsScreen(animeId = animeId)
                })
            }
        }
    }

    private fun goToSearchAnimeScreen() = findNavController().navigate(
        directions = AnimeListFragmentDirections.actionToSearchAnimeScreen()
    )

    private fun goToSeeAllContentsScreen(type: AnimeType) = findNavController().navigate(
        directions = AnimeListFragmentDirections.actionToSeeAllContentListScreen(type = type)
    )

    private fun goToAnimeDetailsScreen(animeId: String) = findNavController().navigate(
        directions = AnimeListFragmentDirections.actionToDetailsScreen(
            argument = DetailsArguments(
                detailsId = animeId,
                entryPointType = EntryPointType.ANIME
            )
        )
    )

    override fun onDestroyView() {
        binding.topAnimeCarousel.adapter = null
        super.onDestroyView()
    }
}