package com.alexandre.skiresort.data

import com.alexandre.skiresort.db.SkiResortDao
import com.alexandre.skiresort.db.model.SkiResort
import com.alexandre.skiresort.domain.model.*
import com.alexandre.skiresort.service.SkiResortListService
import kotlinx.coroutines.flow.*

class SkiResortRepo(private val skiResortListService: SkiResortListService, private val skiResortDao: SkiResortDao) {

    private fun getAllRemoteResorts(): Flow<List<com.alexandre.skiresort.service.model.SkiResort>> = flow {
        emit(emptyList())
        try {
            val networkResult = skiResortListService.getSkiResorts()
            skiResortDao.insertAll(prepareInsertWithFavStatus(toDbModel(networkResult)))
            emit(networkResult)
        } catch (throwable: Throwable) {

        }
    }

    private fun getAllLocalSkiResort(): Flow<List<SkiResort>> = flow {
        skiResortDao.getAllSkiResorts().collect { value ->
            emit(value)
        }
    }

    fun getAllSkiResorts(): Flow<List<com.alexandre.skiresort.domain.model.SkiResort>> = flow {
        getAllLocalSkiResort().combine(getAllRemoteResorts()) { local, remote ->
            toViewModel(remote, local)
        }.collect {
            emit(it)
        }
    }

    suspend fun updateSkiResortFav(skiResortId: Int, isFav: Boolean) {
        skiResortDao.updateFav(skiResortId, isFav)
    }

    private suspend fun prepareInsertWithFavStatus(skiResorts: List<SkiResort>): List<SkiResort> {
        val mutableIterator = skiResorts.iterator()

        // iterator() extension is called here
        for (skiResort in mutableIterator) {
            skiResort.isFav = skiResortDao.isFav(skiResort.skiResortId)
        }
        return skiResorts
    }
}
