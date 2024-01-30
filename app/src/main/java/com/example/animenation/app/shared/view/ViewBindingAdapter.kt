package com.example.animenation.app.shared.view

import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.annotation.DimenRes
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.isVisible = visible
}

@BindingAdapter("setCustomHeight")
fun setCustomHeight(view: View, @DimenRes margin: Int) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        height = view.resources.getDimension(margin).toInt()
    }
}

@BindingAdapter("load_url")
fun loadUrl(view: ShapeableImageView, url: String?) {
    url?.let { view.load(url) }
}

@BindingAdapter("appendValue")
fun appendValue(view: TextView, arrayValue: List<String>?) {
    arrayValue?.let { list ->
        StringBuilder().apply {
            append("Genre: ")
            list.onEachIndexed { index, value ->
                append(value).also {
                    if (index != arrayValue.size -1) append(", ")
                }
            }
            view.text = this
        }
    }
}

@BindingAdapter("streamLink")
fun streamLink(videoView: VideoView, link: String?) {
    link?.let {
        videoView.setVideoURI(Uri.parse(link))
        videoView.start()
    }
}