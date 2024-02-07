package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import android.annotation.SuppressLint
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.navigation.navArgs
import com.imrkjoseph.animenation.app.component.CustomPlayerView
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.foundation.BaseActivity
import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsSmallItem
import com.imrkjoseph.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupCardDetailsSmallItemBinder
import com.imrkjoseph.animenation.databinding.ActivityVideoStreamingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.imrkjoseph.animenation.R.id as appId

@UnstableApi @AndroidEntryPoint
class VideoStreaming : BaseActivity<ActivityVideoStreamingBinding>(bindingInflater = ActivityVideoStreamingBinding::inflate),
    CustomPlayerView.CustomPlayerInterface {

    private val viewModel: VideoStreamingViewModel by viewModels()

    private val args: VideoStreamingArgs by navArgs()

    override fun onActivityCreated() {
        super.onActivityCreated()

        with(binding) {
            configureViews()
            setupObserver()
            setDecorationView(decorView = flags)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) setDecorationView(decorView = flags)
    }

    private fun ActivityVideoStreamingBinding.configureViews() {
        with(videoView) {
            setPlayerInterface(this@VideoStreaming)
            getControlsView(appId.back).setOnClickListener { finish() }
            getControlsView(appId.video_episodes).setOnClickListener {
                videoPlayerMotionLayout.setTransition()
            }
        }

        episodeList.setupBottomList()
    }

    private fun MotionLayout.setTransition() {
        if (currentState == appId.firstScene) transitionToEnd()
        else transitionToStart()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun CustomRecyclerView.setupBottomList() {
        setOnTouchListener { _, event ->
            binding.videoPlayerMotionLayout.onTouchEvent(event)
            return@setOnTouchListener false
        }
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = setupCardDetailsSmallItemBinder(
            dtoRetriever = CardDetailsSmallItem::dto,
            onItemClick = {

            }
        ))
    }

    private fun getControlsView(viewId: Int) = findViewById<View>(viewId)

    private fun setupObserver() {
        with(viewModel) {
            lifecycleScope.launch {
                launch {
                    uiItems.collectLatest {
                        it.updateUi()
                    }
                }
            }

            streamLink.observe(this@VideoStreaming) {
                binding.videoView.apply {
                    if (exoPlayer == null) {
                        setupVideoPlayer(
                            mediaUrl = it.sources?.get(0)?.url.toString()
                        )
                    }
                }
            }
        }
    }

    private fun VideoStreamingUiItems.updateUi() {
        with(binding) {
            episodeItems?.let { bottomList = it }
            executePendingBindings()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getStreamingState()
    }

    override fun onPause() {
        binding.videoView.pauseVideo()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.releasePlayer()
    }

    override fun onMediaInfo(mediaInfo: Int) {
        binding.videoView.apply {
            when(mediaInfo){
                Player.STATE_ENDED -> pauseVideo()
                Player.STATE_READY -> playVideo()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

