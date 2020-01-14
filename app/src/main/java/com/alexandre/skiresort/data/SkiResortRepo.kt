package com.alexandre.skiresort.data

import com.alexandre.skiresort.db.SkiResortDao
import com.alexandre.skiresort.db.model.SkiResort
import com.alexandre.skiresort.domain.model.toDbModel
import com.alexandre.skiresort.domain.model.toViewModel
import com.alexandre.skiresort.domain.model.toViewModelFromDb
import com.alexandre.skiresort.service.SkiResortListService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SkiResortRepo(private val skiResortListService: SkiResortListService, private val skiResortDao: SkiResortDao) {

    fun getAllSkiResorts(): Flow<List<com.alexandre.skiresort.domain.model.SkiResort>> = flow {

        //TODO try catch
        val resultService = skiResortListService.getSkiResorts()

        skiResortDao.insertAll(prepareInsertWithFavStatus(toDbModel(resultService)))

        skiResortDao.getAllSkiResorts().collect { value ->
            emit(toViewModel(resultService, value))
        }
    }

    suspend fun updateSkiResortFav(skiResortId: Int, isFav: Boolean) {
        skiResortDao.updateFav(skiResortId, isFav)

    }

    private suspend fun prepareInsertWithFavStatus(skiResorts : List<SkiResort>): List<SkiResort> {
        val mutableIterator = skiResorts.iterator()

        // iterator() extension is called here
        for (skiResort in mutableIterator) {
            skiResort.isFav = skiResortDao.isFav(skiResort.skiResortId)
        }
        return skiResorts
    }
}
