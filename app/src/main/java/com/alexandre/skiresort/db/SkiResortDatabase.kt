package com.alexandre.skiresort.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexandre.skiresort.db.model.SkiResortLocalModel

/**
 * Database to store all ski resorts
 */
@Database(
        entities = [SkiResortLocalModel::class],
        version = 2,
        exportSchema = false
)
abstract class SkiResortDatabase : RoomDatabase() {

    abstract fun skiResortDao(): SkiResortDao

    companion object {

        @Volatile
        private var INSTANCE: SkiResortDatabase? = null

        fun getInstance(context: Context): SkiResortDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        SkiResortDatabase::class.java, "skiResorts.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}