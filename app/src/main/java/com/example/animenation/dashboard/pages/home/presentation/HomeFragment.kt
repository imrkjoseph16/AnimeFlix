package com.example.animenation.dashboard.pages.home.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.app.shared.binder.component.SectionTitleItemBinder
import com.example.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.example.animenation.app.shared.binder.component.SpaceItemViewDtoBinder
import com.example.animenation.app.shared.binder.component.getMovieItemBinder
import com.example.animenation.app.shared.binder.data.AnimeItem
import com.example.animenation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupAnimeList()
            setupObserver()
        }
    }

    private fun FragmentHomeBinding.configureViews() {
        searchAnime.setOnClickListener {

        }

        newNotification.setOnClickListener {

        }
    }

    private fun FragmentHomeBinding.setupAnimeList() {
        homeScreenItems.setPresetConfig()
        executePendingBindings()
    }

    private fun CustomRecyclerView.setPresetConfig() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        setHasFixedSize(true)
        addItemBindings(viewHolders = SpaceItemViewDtoBinder)
        addItemBindings(viewHolders = SectionTitleItemBinder)
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = getMovieItemBinder(dtoRetriever = AnimeItem::payload))
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(viewModel) {
                    launch {
                        items.observe(viewLifecycleOwner) { uiItems ->
                            binding.homeScreenItems.setItems(uiItems)
                        }
                    }
                }
            }
        }
    }
}