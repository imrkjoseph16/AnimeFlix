package com.example.animenation.dashboard.pages.home.list

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.app.shared.binder.component.SectionTitleItemBinder
import com.example.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.example.animenation.app.shared.binder.component.getWidgetItemBinder
import com.example.animenation.app.shared.binder.data.AnimeItem
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.app.util.autoScroll
import com.example.animenation.dashboard.shared.data.dto.anime.Result
import com.example.animenation.dashboard.shared.presentation.details.DetailsArguments
import com.example.animenation.databinding.FragmentListAnimeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : BaseFragment<FragmentListAnimeBinding>(
    bindingInflater = FragmentListAnimeBinding::inflate
) {

    private val viewModel: AnimeListViewModel by viewModels()

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

        newNotification.setOnClickListener {

        }
    }

    private fun FragmentListAnimeBinding.setupAnimeList() {
        homeScreenItems.setupPresetConfig()
        executePendingBindings()
    }

    private fun CustomRecyclerView.setupPresetConfig() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        addItemBindings(viewHolders = SectionTitleItemBinder)
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = getWidgetItemBinder(
            dtoRetriever = AnimeItem::payload,
            onItemClick = { anime ->
                goToAnimeDetailsScreen(animeId = anime.itemId)
            }
        ))
    }

    private fun setupObserver() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                launch {
                    getAnimeResults.collectLatest {
                        binding.setupTopAnimeCarousel(it.carouselList)
                    }
                }

                launch {
                    items.observe(viewLifecycleOwner) { uiItems ->
                        binding.homeScreenItems.setItems(uiItems)
                    }
                }
            }
        }
    }

    private fun FragmentListAnimeBinding.setupTopAnimeCarousel(results: List<Result>?) {
        results?.let { anime ->
            topAnimeCarousel.apply {
                val topAnimeAdapter = TopAnimeAdapter(topAnimeList = anime)
                adapter = topAnimeAdapter

                carouselIndicator.attachTo(viewPager2 = this)
                autoScroll(milliSeconds = 5000)

                topAnimeAdapter.setOnItemClickListener(object : TopAnimeAdapter.OnCarouselItemClick {
                    override fun onCarouselClick(animeId: String) {
                        goToAnimeDetailsScreen(animeId = animeId)
                    }
                })
            }
        }
    }

    private fun goToSearchAnimeScreen() = findNavController().navigate(
        directions = AnimeListFragmentDirections.actionToSearchAnimeScreen()
    )

    private fun goToAnimeDetailsScreen(animeId: String) = findNavController().navigate(
        directions = AnimeListFragmentDirections.actionToAnimeDetailsScreen(
            argument = DetailsArguments(
                detailsId = animeId,
                entryPointType = EntryPointType.ANIME
            )
        )
    )
}