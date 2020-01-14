package com.alexandre.skiresort.ui.skiresortlist

import androidx.lifecycle.*
import com.alexandre.skiresort.data.SkiResortRepo
import com.alexandre.skiresort.domain.model.SkiResort
import kotlinx.coroutines.launch

class SkiResortListViewModel(private val skiResortRepo: SkiResortRepo) : ViewModel() {

    //list of all the ski resorts
    val skiResortList: LiveData<List<SkiResort>> =
        skiResortRepo.getAllSkiResorts().asLiveData()


    //change the fav value
    fun toggleFav(skiResort: SkiResort) {
        viewModelScope.launch {
            skiResortRepo.updateSkiResortFav(skiResort.skiResortId, !skiResort.isFav)
        }
    }
}