package com.example.animenation.dashboard.pages.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animenation.dashboard.shared.data.dto.anime.Result
import com.example.animenation.databinding.CarouselTopAnimeItemBinding

class TopAnimeAdapter(private val topAnimeList: List<Result> = emptyList()) :
    RecyclerView.Adapter<TopAnimeAdapter.TopAnimeViewHolder>() {

    interface OnCarouselItemClick {
        fun onCarouselClick(animeId: String)
    }

    private lateinit var onCarouselItemClick: OnCarouselItemClick

    fun setOnItemClickListener(onItemClickListener: OnCarouselItemClick) {
        onCarouselItemClick = onItemClickListener
    }

    inner class TopAnimeViewHolder(val binding: CarouselTopAnimeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = topAnimeList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TopAnimeViewHolder(CarouselTopAnimeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: TopAnimeViewHolder, position: Int) {
        holder.binding.apply {
            configureViews(position)
        }
    }

    private fun CarouselTopAnimeItemBinding.configureViews(position: Int) {
        with(topAnimeList[position]) {
            animeTitle.text = title
            animeImage.load(image)

            root.setOnClickListener { onCarouselItemClick.onCarouselClick(animeId = id) }
        }
    }
}