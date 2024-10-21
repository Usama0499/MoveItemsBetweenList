package com.islam.moveitemsbetweenrv

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import androidx.recyclerview.widget.RecyclerView


class DragListener internal constructor(private val listener: Listener) :
    OnDragListener {
    private var isDropped = false

    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1

                val viewSource = event.localState as View
                val viewId = v.id
                val flItem: Int = R.id.frame_layout_item
                val tvEmptyListTop: Int = R.id.tvEmptyListTop
                val tvEmptyListBottom: Int = R.id.tvEmptyListBottom
                val rvTop: Int = R.id.rvTop
                val rvBottom: Int = R.id.rvBottom

                when (viewId) {
                    flItem, tvEmptyListTop, tvEmptyListBottom, rvTop, rvBottom -> {
                        val target: RecyclerView
                        when (viewId) {
                            tvEmptyListTop, rvTop -> target =
                                v.rootView.findViewById<View>(rvTop) as RecyclerView

                            tvEmptyListBottom, rvBottom -> target =
                                v.rootView.findViewById<View>(rvBottom) as RecyclerView

                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        if (viewSource != null) {
                            val source = viewSource.parent as RecyclerView

                            val adapterSource: ListAdapter? = source.adapter as ListAdapter?
                            val positionSource = viewSource.tag as Int
                            val sourceId = source.id

                            val list: String = adapterSource!!.getList()[positionSource]
                            val listSource: MutableList<String> = adapterSource.getList().toMutableList()

                            listSource.removeAt(positionSource);
                            adapterSource.updateList(listSource)
                            adapterSource.notifyDataSetChanged()

                            val adapterTarget: ListAdapter? = target.adapter as ListAdapter?
                            val customListTarget: MutableList<String> = adapterTarget!!.getList().toMutableList()
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list)
                            } else {
                                customListTarget.add(list)
                            }
                            adapterTarget.updateList(customListTarget)
                            adapterTarget.notifyDataSetChanged()

                            if (sourceId == rvBottom && adapterSource.itemCount < 1) {
                                listener.setEmptyListBottom(true)
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false)
                            }
                            if (sourceId == rvTop && adapterSource.itemCount < 1) {
                                listener.setEmptyListTop(true)
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false)
                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}