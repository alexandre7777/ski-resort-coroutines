package com.alexandre.skiresort.ui.skiresortlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexandre.skiresort.R
import com.alexandre.skiresort.domain.model.SkiResortUiModel

class SkiResortViewHolder(view: View)  : RecyclerView.ViewHolder(view) {
    private val skiResortName: TextView = view.findViewById(R.id.skiResortName)
    private val skiResortCountry: TextView = view.findViewById(R.id.skiResortCountry)
    private val skiResortMountain: TextView = view.findViewById(R.id.skiResortMountain)
    private val skiResortSlopesKm: TextView = view.findViewById(R.id.skiResortSlopesKm)
    private val skiResortLifts: TextView = view.findViewById(R.id.skiResortLifts)
    private val skiResortSlopes: TextView = view.findViewById(R.id.skiResortSlopes)
    private val favoriteIV: ImageView = view.findViewById(R.id.favoriteIV)
    private val weatherIV: ImageView = view.findViewById(R.id.weatherIV)

    fun bind(skiResortUiModel: SkiResortUiModel?, toggleFav: (View?, SkiResortUiModel) -> Unit) {
        if (skiResortUiModel != null) {
            showSkiResortData(skiResortUiModel, toggleFav)
        }
    }

    private fun showSkiResortData(skiResortUiModel: SkiResortUiModel, toggleFav: (View?, SkiResortUiModel) -> Unit) {
        skiResortUiModel.apply {
            skiResortName.text = name
            skiResortCountry.text = country
            skiResortMountain.text = mountainRange
            skiResortSlopesKm.text = slopeKm.toString()
            skiResortLifts.text = lifts.toString()
            skiResortSlopes.text = slopes.toString()
            if(isFav) {
                favoriteIV.setImageResource(R.drawable.ic_star_black_24dp)
            }
            else {
                favoriteIV.setImageResource(R.drawable.ic_star_border_black_24dp)
            }
            favoriteIV.setOnClickListener{
                toggleFav(it, skiResortUiModel)
            }
            weather?.let {
                weatherIV.setImageResource(it)
            } ?: run {
                weatherIV.setImageDrawable(null)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): SkiResortViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ski_resort_item, parent, false)
            return SkiResortViewHolder(view)
        }
    }
}