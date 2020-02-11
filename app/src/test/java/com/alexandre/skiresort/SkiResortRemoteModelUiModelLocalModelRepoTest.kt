package com.alexandre.skiresort

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexandre.skiresort.data.SkiResortRepo
import com.alexandre.skiresort.db.SkiResortDao
import com.alexandre.skiresort.db.model.SkiResortLocalModel
import com.alexandre.skiresort.domain.model.SkiResortUiModel
import com.alexandre.skiresort.service.SkiResortListService
import com.alexandre.skiresort.service.model.SkiResortRemoteModel
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SkiResortRemoteModelUiModelLocalModelRepoTest {

    private val skiResortDao = mockkClass(SkiResortDao::class)
    private val skiResortListService = mockkClass(SkiResortListService::class)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `should respond offline data then mixed data when service and db are valid`() = runBlockingTest {
        // Given
        every {
            skiResortDao.getAllSkiResorts()
        } returns flowOf(createExpectedDbData())

        coEvery {
            skiResortListService.getSkiResorts()
        } returns createExpectedRemoteData()

        coEvery {
            skiResortDao.insertAll(createExpectedDbData())
        } returns Unit

        // When
        val result = SkiResortRepo(skiResortListService, skiResortDao).getAllSkiResorts().take(2).toList()

        // Then
        assertEquals(createExpectedResultFirst(), result[0])
        assertEquals(createExpectedResultSecond(), result[1])
    }

    private fun createExpectedResultFirst() =
            listOf(SkiResortUiModel(
                    1,
                    "Val d'Isère",
                    "France",
                    "Alps",
                    300,
                    83,
                    96,
                    true,
                    null))

    private fun createExpectedResultSecond() =
            listOf(SkiResortUiModel(
                    1,
                    "Val d'Isère",
                    "France",
                    "Alps",
                    300,
                    83,
                    96,
                    true,
                    R.drawable.ic_wb_sunny))

    private fun createExpectedDbData() =
            listOf(SkiResortLocalModel(
                    1,
                    "Val d'Isère",
                    "France",
                    "Alps",
                    300,
                    83,
                    96,
                    true))

    private fun createExpectedRemoteData() =
            listOf(SkiResortRemoteModel(1,
                    "Val d'Isère",
                    "France",
                    "Alps",
                    300,
                    83,
                    96,
                    "sunny"))
}