package com.islam.moveitemsbetweenrv

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.islam.moveitemsbetweenrv.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTopRecyclerView();
        initBottomRecyclerView();

        binding.tvEmptyListTop.visibility = View.GONE
        binding.tvEmptyListBottom.visibility = View.GONE
    }

    private fun initTopRecyclerView() {
        binding.rvTop.setLayoutManager(
            LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
            )
        )

        val topList: MutableList<String> = ArrayList()
        topList.add("A")
        topList.add("B")
        topList.add("C")
        topList.add("D")
        topList.add("E")

        val topListAdapter = ListAdapter(topList, this)
        binding.rvTop.setAdapter(topListAdapter)
        binding.tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance())
        binding.rvTop.setOnDragListener(topListAdapter.getDragInstance())
    }

    private fun initBottomRecyclerView() {
        binding.rvBottom.setLayoutManager(
            LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
            )
        )

        val bottomList: MutableList<String> = ArrayList()
        bottomList.add("F")
        bottomList.add("G")
        bottomList.add("H")
        bottomList.add("I")
        bottomList.add("J")

        val bottomListAdapter = ListAdapter(bottomList, this)
        binding.rvBottom.setAdapter(bottomListAdapter)
        binding.tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance())
        binding.rvBottom.setOnDragListener(bottomListAdapter.getDragInstance())
    }


    override fun setEmptyListTop(visibility: Boolean) {
        binding.tvEmptyListTop.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.rvTop.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    override fun setEmptyListBottom(visibility: Boolean) {
        binding.tvEmptyListBottom.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.rvBottom.visibility = if (visibility) View.GONE else View.VISIBLE
    }


}