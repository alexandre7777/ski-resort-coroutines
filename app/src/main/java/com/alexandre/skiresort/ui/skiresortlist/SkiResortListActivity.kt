package com.alexandre.skiresort.ui.skiresortlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandre.skiresort.Injection.provideViewModelFactorySkiResortList
import com.alexandre.skiresort.R
import com.alexandre.skiresort.domain.model.SkiResort
import kotlinx.android.synthetic.main.activity_ski_resort_list.*

class SkiResortListActivity : AppCompatActivity() {

    private lateinit var viewModelSkiResortList: SkiResortListViewModel

    private var adapter = SkiResortAdapter { view: View?, skiResort: SkiResort ->
        viewModelSkiResortList.toggleFav(skiResort)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ski_resort_list)

        viewModelSkiResortList = ViewModelProviders.of(this, provideViewModelFactorySkiResortList(this))
                .get(SkiResortListViewModel::class.java)

        initAdapter()
    }

    private fun initAdapter(){
        list.layoutManager = LinearLayoutManager(applicationContext)
        list.adapter = adapter

        /**
         * Observe changes in the list of ski resort
         */
        viewModelSkiResortList.skiResortList.observe(this, Observer<List<SkiResort>> {
            adapter.submitList(it)
        })
    }
}
