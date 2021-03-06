package com.alexandre.skiresort.domain.model

import com.alexandre.skiresort.R
import com.alexandre.skiresort.db.model.SkiResortLocalModel
import com.alexandre.skiresort.service.model.SkiResortRemoteModel

fun toUiModel(skiResortLocalModelListDb: List<SkiResortLocalModel>,
              skiResortRemoteModelListService: List<SkiResortRemoteModel>):
        List<SkiResortUiModel> {
    return if (skiResortRemoteModelListService.isEmpty()) {
        toUiModelFromDb(skiResortLocalModelListDb)
    } else {
        skiResortRemoteModelListService.map {
            SkiResortUiModel(
                    it.skiResortId,
                    it.name,
                    it.country,
                    it.mountainRange,
                    it.slopeKm,
                    it.lifts,
                    it.slopes,
                    getFavFromList(skiResortLocalModelListDb, it.skiResortId),
                    weather = getDrawableForString(it.weather)
            )
        }
    }
}

fun getFavFromList(skiResortLocalModelListDb: List<SkiResortLocalModel>, id: Int): Boolean {
    val mutableIterator = skiResortLocalModelListDb.iterator()

    // iterator() extension is called here
    for (skiResort in mutableIterator) {
        if (id == skiResort.skiResortId)
            return skiResort.isFav
    }
    return false
}


fun toDbModel(skiResortRemoteModelList: List<SkiResortRemoteModel>):
        List<SkiResortLocalModel> {
    return skiResortRemoteModelList.map {
        SkiResortLocalModel(
                it.skiResortId,
                it.name,
                it.country,
                it.mountainRange,
                it.slopeKm,
                it.lifts,
                it.slopes
        )
    }
}

fun toUiModelFromDb(skiResortLocalModelList: List<SkiResortLocalModel>):
        List<SkiResortUiModel> {
    return skiResortLocalModelList.map {
        SkiResortUiModel(
                it.skiResortId,
                it.name,
                it.country,
                it.mountainRange,
                it.slopeKm,
                it.lifts,
                it.slopes,
                it.isFav
        )
    }
}

fun getDrawableForString(weatherString: String): Int? {
    when (weatherString) {
        "sunny" -> return R.drawable.ic_wb_sunny
        "cloudy" -> return R.drawable.ic_wb_cloudy
        "snow" -> return R.drawable.ic_ac_unit
        "rain" -> return R.drawable.ic_grain
    }
    return null
}