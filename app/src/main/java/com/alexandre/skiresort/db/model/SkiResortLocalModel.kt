package com.alexandre.skiresort.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ski_resorts")
data class SkiResortLocalModel(@PrimaryKey @field:SerializedName("ski_resort_id") val skiResortId: Int,
                               @field:SerializedName("name") val name: String = "",
                               @field:SerializedName("country") val country: String = "",
                               @field:SerializedName("mountain_range") val mountainRange: String = "",
                               @field:SerializedName("slope_km") val slopeKm: Int = 0,
                               @field:SerializedName("lifts") val lifts: Int = 0,
                               @field:SerializedName("slopes") val slopes: Int = 0,
                               @field:SerializedName("is_fav") var isFav: Boolean = false)