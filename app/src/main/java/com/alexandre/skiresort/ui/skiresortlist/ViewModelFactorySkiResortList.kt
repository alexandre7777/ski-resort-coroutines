package com.alexandre.skiresort.ui.skiresortlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexandre.skiresort.data.SkiResortRepo

class ViewModelFactorySkiResortList(private val skiResortRepo: SkiResortRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkiResortListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SkiResortListViewModel(skiResortRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}