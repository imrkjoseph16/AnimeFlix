package com.example.animenation.dashboard.pages.details

import android.net.Uri
import android.widget.VideoView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.databinding.FragmentAnimeDetailsBinding
import kotlinx.coroutines.launch

class AnimeDetailsFragment : BaseFragment<FragmentAnimeDetailsBinding>(FragmentAnimeDetailsBinding::inflate) {

    private val viewModel: AnimeDetailsViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupObserver()
        }
    }

    private fun FragmentAnimeDetailsBinding.configureViews() {
        animeVideo.setupVideo()
    }

    private fun VideoView.setupVideo() {
        setVideoURI(Uri.parse("https://www050.vipanicdn.net/streamhls/aca3be220f104b59aee4017a6812afd8/ep.1.1696601769.1080.m3u8"))
        start()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(viewModel) {

                }
            }
        }
    }
}