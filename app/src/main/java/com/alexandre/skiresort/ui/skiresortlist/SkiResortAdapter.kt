package com.alexandre.skiresort.ui.skiresortlist

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.alexandre.skiresort.domain.model.SkiResort

class SkiResortAdapter(private val toggleFav: (View?, SkiResort) -> Unit) : ListAdapter<SkiResort, RecyclerView.ViewHolder>(SKI_RESORT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SkiResortViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as SkiResortViewHolder).bind(repoItem, toggleFav)
        }
    }


    companion object {
        private val SKI_RESORT_COMPARATOR = object : DiffUtil.ItemCallback<SkiResort>() {
            override fun areItemsTheSame(oldItem: SkiResort, newItem: SkiResort): Boolean =
                    oldItem.skiResortId == newItem.skiResortId

            override fun areContentsTheSame(oldItem: SkiResort, newItem: SkiResort): Boolean =
                    oldItem == newItem
        }
    }
}