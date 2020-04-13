package com.alexandre.skiresort.data

import com.alexandre.skiresort.db.SkiResortDao
import com.alexandre.skiresort.db.model.SkiResortLocalModel
import com.alexandre.skiresort.domain.model.*
import com.alexandre.skiresort.service.SkiResortListService
import com.alexandre.skiresort.service.model.SkiResortRemoteModel
import kotlinx.coroutines.flow.*

class SkiResortRepo(private val skiResortListService: SkiResortListService, private val skiResortDao: SkiResortDao) {

    private fun getAllRemoteResorts(): Flow<List<SkiResortRemoteModel>> = flow {
        emit(emptyList())
        try {
            val networkResult = skiResortListService.getSkiResorts()
            emit(networkResult)
            skiResortDao.insertAll(prepareInsertWithFavStatus(toDbModel(networkResult)))
        } catch (throwable: Throwable) {

        }
    }

    fun getAllSkiResorts(): Flow<List<SkiResortUiModel>> =
        skiResortDao.getAllSkiResorts().combine(getAllRemoteResorts()) { local, remote ->
            toUiModel(local, remote)
    }

    suspend fun updateSkiResortFav(skiResortId: Int, isFav: Boolean) {
        skiResortDao.updateFav(skiResortId, isFav)
    }

    private suspend fun prepareInsertWithFavStatus(skiResortLocalModels: List<SkiResortLocalModel>): List<SkiResortLocalModel> {
        val mutableIterator = skiResortLocalModels.iterator()

        // iterator() extension is called here
        for (skiResort in mutableIterator) {
            skiResort.isFav = skiResortDao.isFav(skiResort.skiResortId)
        }
        return skiResortLocalModels
    }
}
