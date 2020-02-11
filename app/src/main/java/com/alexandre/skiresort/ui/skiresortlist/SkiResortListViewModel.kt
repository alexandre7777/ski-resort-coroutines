package com.alexandre.skiresort.ui.skiresortlist

import androidx.lifecycle.*
import com.alexandre.skiresort.data.SkiResortRepo
import com.alexandre.skiresort.domain.model.SkiResortUiModel
import kotlinx.coroutines.launch

class SkiResortListViewModel(private val skiResortRepo: SkiResortRepo) : ViewModel() {

    //list of all the ski resorts
    val skiResortUiModelList: LiveData<List<SkiResortUiModel>> =
        skiResortRepo.getAllSkiResorts().asLiveData()


    //change the fav value
    fun toggleFav(skiResortUiModel: SkiResortUiModel) {
        viewModelScope.launch {
            skiResortRepo.updateSkiResortFav(skiResortUiModel.skiResortId, !skiResortUiModel.isFav)
        }
    }
}