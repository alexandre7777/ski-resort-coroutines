package com.alexandre.skiresort.ui.skiresortlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandre.skiresort.Injection.provideViewModelFactorySkiResortList
import com.alexandre.skiresort.R
import com.alexandre.skiresort.domain.model.SkiResortUiModel
import kotlinx.android.synthetic.main.activity_ski_resort_list.*

class SkiResortListActivity : AppCompatActivity() {

    private lateinit var viewModelSkiResortList: SkiResortListViewModel

    private var adapter = SkiResortAdapter { view: View?, skiResortUiModel: SkiResortUiModel ->
        viewModelSkiResortList.toggleFav(skiResortUiModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ski_resort_list)

        viewModelSkiResortList = ViewModelProvider(this, provideViewModelFactorySkiResortList(this))
                .get(SkiResortListViewModel::class.java)

        initAdapter()
    }

    private fun initAdapter(){
        list.layoutManager = LinearLayoutManager(applicationContext)
        list.adapter = adapter

        /**
         * Observe changes in the list of ski resort
         */
        viewModelSkiResortList.skiResortUiModelList.observe(this, Observer<List<SkiResortUiModel>> {
            adapter.submitList(it)
        })
    }
}
