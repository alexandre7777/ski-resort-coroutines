package com.alexandre.skiresort.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexandre.skiresort.db.model.SkiResort

/**
 * DAO for ski resorts
 */
@Dao
interface SkiResortDao{

    //Add a list of ski resorts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(skiResortList: List<SkiResort>)

    //Get all the ski resorts
    @Query("SELECT skiResortId, name, country, mountainRange, slopeKm, lifts, slopes, isFav FROM ski_resorts")
    fun getAllSkiResorts(): LiveData<List<SkiResort>>

    @Query("UPDATE ski_resorts SET isFav = :isFav WHERE skiResortId = :skiResortId")
    fun updateFav(skiResortId : Int, isFav : Boolean)

    //For a book isbn get the number of books in the basket
    @Query("SELECT isFav FROM ski_resorts WHERE skiResortId = :skiResortId")
    fun isFav(skiResortId : Int): Boolean
}