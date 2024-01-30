package com.example.animenation.dashboard.shared.presentation.video.presentation

import android.widget.VideoView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.animenation.app.foundation.BaseActivity
import com.example.animenation.app.util.MediaInfo
import com.example.animenation.app.util.TapGesture
import com.example.animenation.app.util.infoListener
import com.example.animenation.app.util.onTouchListener
import com.example.animenation.app.util.safeRunWithDelay
import com.example.animenation.dashboard.shared.presentation.video.dto.StreamingLink
import com.example.animenation.databinding.ActivityVideoStreamingBinding
import com.example.animenation.databinding.WidgetVideoControlsItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class VideoStreaming : BaseActivity<ActivityVideoStreamingBinding>(bindingInflater = ActivityVideoStreamingBinding::inflate) {

    private val viewModel: VideoStreamingViewModel by viewModels()

    private var videoDurationJob: Job? = null

    override fun onActivityCreated() {
        super.onActivityCreated()

        with(binding) {
            configureViews()
            setupObserver()
            setDecorationView(decorView = flags)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) setDecorationView(decorView = flags)
    }

    override fun onUserLeaveHint() = enterPictureInPictureMode()

    private fun ActivityVideoStreamingBinding.configureViews() {
        canLoading = true
        streamVideo.infoListener { mediaInfo ->
            streamLoading.apply {
                canLoading = when(mediaInfo) {
                    MediaInfo.RENDER_START -> false
                    MediaInfo.BUFFERING_START,
                    MediaInfo.RENDER_FAILED -> true
                    else -> false
                }
            }
        }

        widgetControls.apply {
            streamVideo.setOnClickListener {
                setupControlState()
            }

            back.setOnClickListener {
                finish()
            }

            playPause.setOnClickListener {
                streamVideo.setupVideoState()
            }

            leftScreen.onTouchListener(
                context = this@VideoStreaming,
                onDoubleTapClick = { updateVideoPosition(tapGesture = TapGesture.LEFT_TAP) },
                onSingleTapClick = { setupControlState() }
            )

            rightScreen.onTouchListener(
                context = this@VideoStreaming,
                onDoubleTapClick = { updateVideoPosition(tapGesture = TapGesture.RIGHT_TAP) },
                onSingleTapClick = { setupControlState() }
            )
        }
    }


    private fun setupObserver() {
        with(viewModel) {
            streamLink.observe(this@VideoStreaming, ::setupVideoConfiguration)
            getStreamingState()
        }
    }

    private fun setupVideoConfiguration(resultDetails: StreamingLink) {
        binding.streamDetails = resultDetails
        videoDurationJob.safeRunWithDelay(
            context = this,
            durationMilliseconds = 1000,
            block = ::updateCurrentDuration
        ).also { videoDurationJob = it }
    }

    private fun updateCurrentDuration() {
        binding.apply {
            if (widgetControls.videoProgress.progress >= 100) videoDurationJob?.cancel()

            val currentPosition = streamVideo.currentPosition
            widgetControls.apply {
                videoProgress.progress = currentPosition * 100 / streamVideo.duration
                textVideoProgress.text = viewUtil.msToTimeConverter(currentPosition)
            }
        }
    }

    private fun VideoView.setupVideoState() {
        binding.widgetControls.apply {
            canStreamVideo = isPlaying
            if (isPlaying) pause()
            else start()
        }
    }

    private fun updateVideoPosition(tapGesture: TapGesture) {
        with(binding) {
            streamVideo.apply {
                seekTo(getUpdatedPosition(tapGesture = tapGesture))
                start()
            }
        }
    }

    private fun WidgetVideoControlsItemBinding.setupControlState() {
        root.isVisible = root.isVisible.not()
    }

    private fun VideoView.getUpdatedPosition(tapGesture: TapGesture) =
        currentPosition +
        if (tapGesture == TapGesture.LEFT_TAP) (-5000)
        else (+5000)
}

